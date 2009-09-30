/**
 * 
 */
package de.tuberlin.uebb.emodelica;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Stack;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProvider;

import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.emodelica.util.ParserFactory;
import de.tuberlin.uebb.modelica.im.generated.moparser.LexerDefs;
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

	private static HashMap<String, Model> repository = new HashMap<String, Model>();
	private static final ILexer lexer = new NonStaticLexer();
	private static final Automaton parser = ParserFactory.getAutomatonInstance();
	
	public static void updateModel(IFile file, Model model) {
		repository.put(file.getFullPath().toString(), model);
	}
	
	public static Model getModelForFile(IFile file) {
		String path = file.getFullPath().toString();
		if (repository.containsKey(path))
			return repository.get(path);
		
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
    		repository.put(path, newModel);
    		return newModel;
		}
		return null;
	}
	
}
