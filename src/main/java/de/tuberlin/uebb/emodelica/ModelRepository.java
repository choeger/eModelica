/**
 * 
 */
package de.tuberlin.uebb.emodelica;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProvider;

import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.emodelica.util.ParserFactory;
import de.tuberlin.uebb.modelica.im.impl.generated.moparser.LexerDefs;
import de.tuberlin.uebb.page.exceptions.ParserException;
import de.tuberlin.uebb.page.grammar.symbols.Terminal;
import de.tuberlin.uebb.page.lexer.ILexer;
import de.tuberlin.uebb.page.lexer.NonStaticLexer;
import de.tuberlin.uebb.page.lexer.exceptions.InvalidCharacterException;
import de.tuberlin.uebb.page.lexer.exceptions.LexerException;
import de.tuberlin.uebb.page.parser.Automaton;
import de.tuberlin.uebb.page.parser.symbols.Absy;

/**
 * @author choeger
 *
 */
public class ModelRepository {

	static class ModelGenerator extends Job {

		public ModelGenerator(String name) {
			super(name);
		}

		private final ConcurrentLinkedQueue<IFile> sources = new ConcurrentLinkedQueue<IFile>();
		
		@Override
		protected IStatus run(IProgressMonitor monitor) {
			if (parser == null)
				parser = ParserFactory.getAutomatonInstance();
			while(true) {
				IFile next = null;
				next = sources.poll();

				if (next != null) {
					Model model = getModelForFile(next);
					model.compact();
					System.err.println("BACKGROUND MODEL DONE: " + next + " " + sources.size() + " TODO");
				}
				if (sources.isEmpty()) sleep();
			}
		}
	}
	
	private static HashMap<String, Model> repository = new HashMap<String, Model>();
	private static final ILexer lexer = new NonStaticLexer();
	private static Automaton parser;
	private static final ModelGenerator synchronizer = new ModelGenerator("synchronize Modelica source models");
	
	public static void startSync() {
		synchronizer.setSystem(true);
		synchronizer.schedule();
	}
	
	public static void enqueueFile(IFile file) {
		synchronized(synchronizer.sources) {
			synchronizer.sources.add(file);
		}
		synchronizer.wakeUp();
	}
	
	public static void updateModel(IFile file, Model model) {
		repository.put(file.getFullPath().toString(), model);
	}
	
	/**
	 * This method returns the (compacted) Model for the given file
	 * or null if the synchronizer did not finish the file yet
	 * @param file
	 * @return
	 */
	public static Model getModelForFileUnblocking(IFile file) {
		final String path = file.getFullPath().toString();
		synchronized (repository) {
			if (repository.containsKey(path))
				return repository.get(path);
			else {
				synchronizer.sources.add(file);
				synchronizer.wakeUp();
			}
		}
		return null;
	}
	
	
	public static Model getModelForFile(IFile file) {
		final String path = file.getFullPath().toString();
		synchronized (repository) {
			if (repository.containsKey(path))
				return repository.get(path);
		}
		
		Stack<Terminal> inputStack = null;
		BufferedReader contentReader;
		try {
			contentReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(file.getContents())));
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}

		try {
			inputStack = lexer.getInputTokens(contentReader, LexerDefs.lexerDefs());
		} catch (InvalidCharacterException e) {
			/* if we encounter a lexer problem, 
			 * simply set inputStack to null.
			 * Parser will handle that, save the lexer error for the user
			 */
			e.printStackTrace();
			inputStack = null;
			//TODO: return error Model
			return null;
		} catch (LexerException e) {
			e.printStackTrace();
			//TODO: return error Model
			return null;
		}
		
		parser.init();
		parser.setInputStack(inputStack);
		
		int retVal = 1;
		try {			
			retVal = parser.runParser();
		} catch (ParserException e) {
			System.err.println(e.getMessage());
			//TODO: add recovery and error handling
			return null;
		}
		
		System.err.println("done parsing " + path);
		
		if (retVal == 0) {
			System.err.println("parsing successfull");
			Absy rootAbsy = (Absy)parser.getTokenStack().pop();
    		IDocumentProvider documentProvider = new TextFileDocumentProvider();
    		try {
				documentProvider.connect(file);
			} catch (CoreException e) {
				e.printStackTrace();
				return null;
			}
			Model newModel = new Model(documentProvider.getDocument(file), lexer, rootAbsy);
    		
			synchronized (repository) {
    			repository.put(path, newModel);
    		}

    		return newModel;
		}
		return null;
	}
	
}
