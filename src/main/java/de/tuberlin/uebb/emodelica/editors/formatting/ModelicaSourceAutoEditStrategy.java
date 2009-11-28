package de.tuberlin.uebb.emodelica.editors.formatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;

public class ModelicaSourceAutoEditStrategy implements IAutoEditStrategy {

	final Pattern indentionPattern = Pattern.compile("^\\s*");
	final Pattern idePattern = Pattern.compile("((_|\\p{Alpha})+(_|\\p{Alnum})*)|('[(?<=\\\\)'[^']]*?(?<!\\\\)')");
	final Matcher matcher = idePattern.matcher("");
	
	private boolean quotes = false;
	
	@Override
	public void customizeDocumentCommand(IDocument document,
			DocumentCommand command) {
		
		try {
			final char next = document.getChar(command.offset);								
			if (command.text.endsWith("\n")) {
				final int line = document.getLineOfOffset(command.offset);
				final int start = document.getLineLength(line);
				final int end = document.getLineOffset(line);
				
				final String lineStr = (document.get(start, end)).trim();
				if (lineStr.startsWith("class")) {
					matcher.reset(lineStr);
					matcher.region(6, lineStr.length() - 6);
					if (matcher.matches()) {
						
						command.text += "\n";
						command.text += "end " + matcher.group() + ";\n";
						command.shiftsCaret = false;
					}
				}
			}
			
		} catch (BadLocationException e) {
			return;
		}		
	}

}
