/**
 * 
 */
package de.tuberlin.uebb.emodelica.model;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import de.tuberlin.uebb.modelica.im.impl.generated.moparser.NT_Stored_Definition;
import de.tuberlin.uebb.page.exceptions.ParserException;
import de.tuberlin.uebb.page.exceptions.UnexpectedEOFException;
import de.tuberlin.uebb.page.grammar.symbols.Terminal;
import de.tuberlin.uebb.page.incremental.parser.IIncrementalParser;
import de.tuberlin.uebb.page.incremental.parser.impl.IncrementalParser;
import de.tuberlin.uebb.page.lexer.ILexer;
import de.tuberlin.uebb.page.lexer.NonStaticLexer;
import de.tuberlin.uebb.page.lexer.exceptions.InvalidCharacterException;
import de.tuberlin.uebb.page.lexer.exceptions.LexerException;
import de.tuberlin.uebb.page.parser.Automaton;
import de.tuberlin.uebb.page.parser.LexerError;
import de.tuberlin.uebb.page.parser.ParseError;
import de.tuberlin.uebb.page.parser.symbols.Absy;
import de.tuberlin.uebb.page.parser.symbols.IAbsy;
import de.tuberlin.uebb.page.parser.util.Range;

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
				automaton.runParser();
			} catch (ParserException e) {
				//System.err.println(e.getMessage());
				arg0.done();
				parser.getErrorSet().add(new ParseError(lexer.getCachedInput().size()-1,lexer.getCachedInput().size()-1,e.getMessage()));
				return new Status(Status.OK,EModelicaPlugin.PLUGIN_ID,retVal,"parsing failed: " + e.getMessage(),e);
			}
			arg0.done();
			return new Status(Status.OK,EModelicaPlugin.PLUGIN_ID,retVal,"parsing finished",null);
		}
	}

	private static final int LOOKAHEAD = 3;
		
	private Model model = null;
	private IModelContentProvider contentProvider = null;
	private Set<IModelChangedListener> listeners = new HashSet<IModelChangedListener>();
	private ParseError lexerError = null;
	private final ILexer lexer = new NonStaticLexer();
	private final Automaton parser = ParserFactory.getAutomatonInstance();
	private final ModelicaParseJob parseJob = new ModelicaParseJob("resource",parser);
	private final ModelicaEditor modelicaEditor;

	private Range lastChanged;

	private ArrayList<Terminal> lastParsedInput;
	
	public ModelicaModelManager(final ModelicaEditor modelicaEditor) {
		this.modelicaEditor = modelicaEditor;
		parseJob.addJobChangeListener(new JobChangeAdapter() {
	        public void done(IJobChangeEvent event) {
	        	if (event.getResult().isOK()) {
	        		Absy rootAbsy = null;
	        		if (!parser.getTokenStack().isEmpty() && (parser.getTokenStack().peek() instanceof Absy))
	        			rootAbsy = (Absy)parser.getTokenStack().pop();
	        		parsingDone(rootAbsy);
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
		long startTime = System.currentTimeMillis();
		Stack<Terminal> inputStack = doLexing();
		
		if (inputStack == null) {
			parsingDone(null);
			return;
		}
		
		if (model == null || model.isCompacted()) {
			parseModel(inputStack, null);
			lastParsedInput = lexer.getCachedInput();
		} else {
			lastChanged = applyDiff(lastParsedInput, lexer.getCachedInput());
			long endTime = System.currentTimeMillis();
			System.err.println("CHANGE: Lexing finished after " + (endTime - startTime) + "ms");
			if (lastChanged != null && lastChanged.getStartToken() < lexer.getCachedInput().size())
				incrementalParseModel(inputStack, lastChanged);
			lastParsedInput = lexer.getCachedInput();
		}
	}

	private void incrementalParseModel(Stack<Terminal> inputStack, Range changed) {		
		IIncrementalParser iParser = new IncrementalParser();
		
		Set<ParseError> preserved = new HashSet<ParseError>();
		
		for (ParseError parseError : parser.getErrorSet()) {
			if(!(parseError instanceof LexerError) && (parseError.getEndOffset() < changed.getStartToken()))
					preserved.add(parseError);
		}
		
		iParser.setup(parser, lexer);
		try {
			//Modelica Lookahead
			changed.setStartToken(Math.max(0,changed.getStartToken() - LOOKAHEAD));
			parser.getErrorSet().addAll(preserved);
			IAbsy root = iParser.doParsing(model.getChild(), changed);
			parsingDone(root);
			
		} catch (ParserException e) {
			if (e instanceof UnexpectedEOFException) {
				parser.getErrorSet().addAll(preserved);
				parser.getErrorSet().add(new ParseError(lexer.getCachedInput().size() - 1,lexer.getCachedInput().size() - 1, e.getMessage()));
				parsingDone(null);
			}
			else e.printStackTrace();
		}
	}

	@Override
	public void registerListener(IModelChangedListener listener) {
		listeners.add(listener);
		listener.modelChanged(model, model);
	}
	
	private void parseModel(Stack<Terminal> inputStack, Range damagedRegion) {
		if (parseJob.getState() == Job.RUNNING)
			parseJob.cancel();
		
		if (damagedRegion != null && model.getChild() != null) {
			/* incremental parsing */
			IIncrementalParser iParser = new IncrementalParser();
			iParser.setup(parser, lexer);
			try {
				final IAbsy newChild = iParser.doParsing(model.getChild(), damagedRegion);
				
				if (newChild instanceof NT_Stored_Definition) {
					model.updateFromAbsy(newChild, lexer);
				}
				for (IModelChangedListener l : listeners)
					l.modelChanged(model, model);
				
			} catch (ParserException e) {
				e.printStackTrace();
			}
		} else {
			/* batch parsing */
			parser.init();
			parser.setInputStack(inputStack);
			parseJob.schedule();
		}
	}

	private Stack<Terminal> doLexing() {
		//parsing
		Stack<Terminal> inputStack = null;
		BufferedReader contentReader = contentProvider.getContent();
		lexerError = null;
		
		try {
			inputStack = lexer.getInputTokens(contentReader, de.tuberlin.uebb.modelica.im.impl.generated.moparser.LexerDefs.lexerDefs());
		} catch (InvalidCharacterException e) {
			/* if we encounter a lexer problem, 
			 * simply set inputStack to null.
			 * Parser will handle that, save the lexer error for the user
			 */
			inputStack = null;
			lexerError = new LexerError(e.getOffset(),e.getOffset() + 1, e.getMessage());
			return null;
		} catch (LexerException e) {
			e.printStackTrace();
			return null;
		}
		return inputStack;
	}

	private Range applyDiff(List<Terminal> oldInput, List<Terminal> cachedInput) {
		//common prefix elimination
		int left = 0;
		final int maxl = Math.min(oldInput.size(), cachedInput.size());
		while(left < maxl) {
			final Terminal oldTerminal = oldInput.get(left);
			final Terminal newTerminal = cachedInput.get(left);
			//System.err.println("PREFIX: " + oldTerminal + " == " + newTerminal + "??");
			if (oldTerminal.equals(newTerminal) && oldTerminal.valueHash == newTerminal.valueHash) {
				oldTerminal.setStartOffset(newTerminal.getStartOffset());
				oldTerminal.setEndOffset(newTerminal.getEndOffset());
				cachedInput.set(left, oldTerminal);
			} else break;
			left++;
		}
		
		//common suffix elimination		
		if (oldInput.size() == cachedInput.size() && cachedInput.size() == (left+1)) {
			return null;
		}
		final int maxr = Math.min(oldInput.size() - left, cachedInput.size() - left);	
		
		int right = 1;
		while(right <= maxr) {
			final Terminal oldTerminal = oldInput.get(oldInput.size() - right);
			final Terminal newTerminal = cachedInput.get(cachedInput.size() - right);
			//System.err.println("SUFFIX: " + oldTerminal + " == " + newTerminal + "??");
			if (oldTerminal.equals(newTerminal) && oldTerminal.valueHash == newTerminal.valueHash) {
				oldTerminal.setRange(newTerminal.getRange());
				oldTerminal.setStartOffset(newTerminal.getStartOffset());
				oldTerminal.setEndOffset(newTerminal.getEndOffset());
				cachedInput.set(cachedInput.size() - right, oldTerminal);
			} else break;
			right++;
		}
		
		return new Range(left, (cachedInput.size() - right) + 1);
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

	private void parsingDone(IAbsy rootAbsy) {
		IEditorInput input = modelicaEditor.getEditorInput();
		IFile file = ((FileEditorInput) input).getFile();
		//System.err.println("Parsing finished. Got: " + rootAbsy + " " + rootAbsy.getClass().getCanonicalName());
		if (rootAbsy instanceof NT_Stored_Definition) {
			Model newModel = new Model(contentProvider.getDocument(), lexer, rootAbsy);
			for (IModelChangedListener l : listeners)
				l.modelChanged(model, newModel);
			model = newModel;
			ModelRepository.updateModel(file, newModel);
		} else if (model != null) {
			model.setInput(lexer.getCachedInput());
			modelicaEditor.modelChanged(model, model);
		} else {
			model = new Model(contentProvider.getDocument(), lexer, null);
			modelicaEditor.modelChanged(null, model);
			ModelRepository.updateModel(file, model);
		}
	}
}
