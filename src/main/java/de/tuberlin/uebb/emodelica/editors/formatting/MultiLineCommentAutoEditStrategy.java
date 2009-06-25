/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors.formatting;

import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;

/**
 * @author choeger
 * This AutoEditStrategy adds a star to a multiline comment when the user adds a newline
 */
public class MultiLineCommentAutoEditStrategy implements IAutoEditStrategy {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.IAutoEditStrategy#customizeDocumentCommand(org.eclipse.jface.text.IDocument, org.eclipse.jface.text.DocumentCommand)
	 */
	@Override
	public void customizeDocumentCommand(IDocument document,
			DocumentCommand command) {
		
		if (command.text.startsWith("\n"))
				command.text += "* ";
	}

}
