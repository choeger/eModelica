package de.tuberlin.uebb.emodelica.editors.formatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;

public class ModelicaSourceAutoEditStrategy implements IAutoEditStrategy {

	final Pattern indentionPattern = Pattern.compile("^\\s*");
	
	@Override
	public void customizeDocumentCommand(IDocument document,
			DocumentCommand command) {
		
		//Bracket completion
		if (command.text.startsWith("(")) {
			command.text+=")";
		}
		
	}

}
