/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors.presentation;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;

/**
 * @author choeger
 * This class has only one purpose: Add a Multiline Rule 
 * that breaks on EOF without any escape Characters
 *
 */
public class MultiLineCommentRule extends MultiLineRule {

	public MultiLineCommentRule(String startSequence, String endSequence,
			IToken token) {
		super(startSequence, endSequence, token);
		this.fBreaksOnEOF = true;
	}
}
