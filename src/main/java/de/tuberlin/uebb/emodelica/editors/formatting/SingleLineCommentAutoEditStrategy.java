/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors.formatting;

import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;

/**
 * @author choeger
 * This AuotEditStrategy adds a new Single line comment if a newline is added after another single line comment
 */
public class SingleLineCommentAutoEditStrategy implements IAutoEditStrategy {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.IAutoEditStrategy#customizeDocumentCommand(org.eclipse.jface.text.IDocument, org.eclipse.jface.text.DocumentCommand)
	 */
	@Override
	public void customizeDocumentCommand(IDocument document,
			DocumentCommand command) {
		
		if (command.text.startsWith("\n"))
				command.text += "//";
	}

}
