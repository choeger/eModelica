/**
 * 
 */
package de.tuberlin.uebb.emodelica.model;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.ModelRepository;
import de.tuberlin.uebb.emodelica.editors.ModelicaEditor;
import de.tuberlin.uebb.emodelica.util.ParserFactory;
import de.tuberlin.uebb.page.exceptions.ParserException;
import de.tuberlin.uebb.page.grammar.symbols.Terminal;
import de.tuberlin.uebb.page.lexer.ILexer;
import de.tuberlin.uebb.page.lexer.NonStaticLexer;
import de.tuberlin.uebb.page.lexer.exceptions.InvalidCharacterException;
import de.tuberlin.uebb.page.lexer.exceptions.LexerException;
import de.tuberlin.uebb.page.parser.Automaton;
import de.tuberlin.uebb.page.parser.ParseError;
import de.tuberlin.uebb.page.parser.symbols.Absy;

/**
 * @author choeger
 *
 */
public class ModelicaModelManager implements IModelManager {

	class ModelicaParseJob extends Job {
		private Automaton automaton;
		
		public ModelicaParseJob(String resourceName, Automaton automaton) {
			super("Reconciling " + resourceName);
			this.automaton = automaton;
		}
		
		@Override
		protected IStatus run(IProgressMonitor arg0) {
			int retVal = 1;
			arg0.beginTask("parsing", automaton.getInputStack().size());
			try {			
				while (retVal >= 1) {
					final int l = automaton.getInputStack().size();
					if (arg0.isCanceled())
						return Status.CANCEL_STATUS;
					retVal = automaton.doParseStep();
					//one step per shift
					if (automaton.getInputStack().size() < l)
						arg0.worked(1);
				}
			} catch (ParserException e) {
				System.err.println(e.getMessage());
				return new Status(Status.ERROR,EModelicaPlugin.PLUGIN_ID,retVal,"parsing failed: " + e.getMessage(),e);
			}
			System.err.println("parsing done: " + retVal);
			arg0.done();
			return new Status(Status.OK,EModelicaPlugin.PLUGIN_ID,retVal,"parsing finished",null);
		}
	}
	
	private Model model = null;
	private IModelContentProvider contentProvider = null;
	private Set<IModelChangedListener> listeners = new HashSet<IModelChangedListener>();
	private ParseError lexerError = null;
	private final ILexer lexer = new NonStaticLexer();
	private final Automaton parser = ParserFactory.getAutomatonInstance();
	private final ModelicaParseJob parseJob = new ModelicaParseJob("resource",parser);
	
	public ModelicaModelManager(final ModelicaEditor modelicaEditor) {
		parseJob.addJobChangeListener(new JobChangeAdapter() {
	        public void done(IJobChangeEvent event) {
	        	if (event.getResult().isOK()) {
	        		Absy rootAbsy = null;
	        		if (!parser.getTokenStack().isEmpty() && (parser.getTokenStack().peek() instanceof Absy))
	        			rootAbsy = (Absy)parser.getTokenStack().pop();
	        		
	        		Model newModel = new Model(contentProvider.getDocument(), lexer, rootAbsy);
	        		for (IModelChangedListener l : listeners)
	        			l.modelChanged(model, newModel);
	        		model = newModel;
	        		IEditorInput input = modelicaEditor.getEditorInput();
	        		IFile file = ((FileEditorInput) input).getFile();
	        		ModelRepository.updateModel(file, newModel);
	        	}
	        }
	     });
	}
	
	/* (non-Javadoc)
	 * @see de.tu_berlin.uebb.emodelica.model.IModelManager#getModel()
	 */
	public Model getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	/* (non-Javadoc)
	 * @see de.tu_berlin.uebb.emodelica.model.IModelManager#setContentProvider(de.tu_berlin.uebb.emodelica.model.ModelContentProvider)
	 */
	public void setContentProvider(IModelContentProvider provider) {
		this.contentProvider = provider;
	}

	@Override
	public void contentChanged() {
		parseModel();
	}

	@Override
	public void registerListener(IModelChangedListener listener) {
		listeners.add(listener);
		listener.modelChanged(model, model);
	}
	
	private void parseModel() {
		if (parseJob.getState() == Job.RUNNING)
			parseJob.cancel();

		//parsing
		Stack<Terminal> inputStack = null;
		BufferedReader contentReader = contentProvider.getContent();
		lexerError = null;
		
		try {
			inputStack = lexer.getInputTokens(contentReader, de.tuberlin.uebb.modelica.im.generated.moparser.LexerDefs.lexerDefs());
		} catch (InvalidCharacterException e) {
			/* if we encounter a lexer problem, 
			 * simply set inputStack to null.
			 * Parser will handle that, save the lexer error for the user
			 */
			e.printStackTrace();
			inputStack = null;
			lexerError = new ParseError(e.getOffset(),e.getOffset() + 1, e.getMessage());
			return;
		} catch (LexerException e) {
			e.printStackTrace();
			return;
		}
		
		parser.init();
		parser.setInputStack(inputStack);
		System.err.println("in: " + inputStack.size());
		System.err.println("starting job...");
		parseJob.schedule();		
	}

	@Override
	public IReconcilingStrategy getReconcilingStrategy() {
		return new SimpleModelicaReconcilingStrategy(this);
	}
	
	@Override
	public Set<ParseError> getParseErrors() {
		if (lexerError == null)
			return parser.getErrorSet();
		else {
			HashSet<ParseError> errors = new HashSet<ParseError>();
			errors.add(lexerError);
			return errors;
		}
	}
	
}
