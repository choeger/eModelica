/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors.presentation;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWhitespaceDetector;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import de.tuberlin.uebb.emodelica.Constants;

/**
 * @author choeger
 *
 */
public class ModelicaScannerRules {
	
	public static Color backgroundColor = null;
	static class ModelicaWordDetector implements IWordDetector {

		@Override
		public boolean isWordPart(char c) {
			//that should be suffice
			return Character.isJavaIdentifierPart(c);
		}

		@Override
		public boolean isWordStart(char c) {
			return Character.isJavaIdentifierStart(c);
		}
		
	}
	
	static class ModelicaWhitespaceDetector implements IWhitespaceDetector {

		@Override
		public boolean isWhitespace(char c) {
			return Character.isWhitespace(c);
		}
		
	}
	
	private static Token kwToken() {
		return new Token(new TextAttribute(ModelicaColors.kwTokenColor, backgroundColor, SWT.BOLD));
	}
	
	private static Set<IRule> getKWRules() {
		HashSet<IRule> kwRules = new HashSet<IRule>();
		String[] modelicaKeywords =  {
				"within","final","encapsulated","partial","class","model","record",
				"block","expandable","connector","type","package","function","end",
				"enumeration","der","extends","public protected","external","redeclare",
				"final","inner","outer","replaceable","import","constrainedby","flow",
				"discrete","parameter","constant","input","output","if","each","initial",
				"equation","algorithm","break","rerturn","then","elseif","else","for",
				"loop","in","while","when","elsewhen","connect","or","and","not",
				"false","true","annotation"
		};
		
		WordRule wordRule = new WordRule(new ModelicaWordDetector(), getDefaultToken());
		for (String kw : modelicaKeywords)
			wordRule.addWord(kw, kwToken());
		
		kwRules.add(wordRule);
		
		return kwRules;
	}
	
	
	public static IRule[] getSyntaxScannerRules() {
		HashSet<IRule> allRules = new HashSet<IRule>();
		
		allRules.addAll(getKWRules());
		allRules.add(new WhitespaceRule(new ModelicaWhitespaceDetector()));
		
		return allRules.toArray(new IRule[]{});
	}

	public static IRule[] getStringPartScannerRules() {
		//TODO: different Token for escapeSequences
		
		return new IRule[] {};
	}
	
	public static IToken getStringDefaultToken() {
		return new Token(new TextAttribute(ModelicaColors.stringColor ));
	}

	public static IToken getDefaultToken() {
		return new Token(new TextAttribute(ModelicaColors.foregroundColor, backgroundColor, 0));
	}
	
	public static IPredicateRule[] getPartitioningRules() {
		IToken stringToken = new Token(Constants.PART_MODELICA_STRING);
		IToken mCommentToken = new Token(Constants.PART_MODELICA_MULTI_LINE_COMMENT);
		IToken sCommentToken = new Token(Constants.PART_MODELICA_SINGLE_LINE_COMMENT);
		IPredicateRule[] rules = new IPredicateRule[] {
				new MultiLineRule("\"", "\"", stringToken, '\\', true),
				new MultiLineCommentRule("/*","*/",mCommentToken),
				new SingleLineRule("//",null, sCommentToken)
		};
		
		return rules;
	}

	public static IToken getCommentDefaultToken() {
		return new Token(new TextAttribute(ModelicaColors.commentColor));
	}

	public static IRule[] getCommentPartScannerRules() {
		return new IRule[] {};
	}
}
