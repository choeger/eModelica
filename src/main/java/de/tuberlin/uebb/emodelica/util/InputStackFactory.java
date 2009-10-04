/**
 * 
 */
package de.tuberlin.uebb.emodelica.util;

import java.io.BufferedReader;
import java.util.Stack;

import de.tuberlin.uebb.page.grammar.symbols.Terminal;
import de.tuberlin.uebb.page.lexer.Lexer;
import de.tuberlin.uebb.page.lexer.exceptions.LexerException;

/**
 * @author choeger
 * here we can add a cache later
 */
public class InputStackFactory {
	public static Stack<Terminal> getInputStack(BufferedReader contentReader) throws LexerException {
		Stack<Terminal> inputStack = Lexer.getInputTokens(contentReader, de.tuberlin.uebb.modelica.im.impl.generated.moparser.LexerDefs.lexerDefs());
		return inputStack;
	}
}
