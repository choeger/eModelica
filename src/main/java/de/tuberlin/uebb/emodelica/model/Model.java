/** 
 * Test
 */
package de.tuberlin.uebb.emodelica.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;

import de.tuberlin.uebb.modelica.im.ClassNode;
import de.tuberlin.uebb.modelica.im.generated.moparser.NT_Stored_Definition;
import de.tuberlin.uebb.modelica.im.generator.AbsyToIM;
import de.tuberlin.uebb.page.grammar.symbols.Terminal;
import de.tuberlin.uebb.page.parser.symbols.Absy;
import de.tuberlin.uebb.page.parser.util.Range;

/**
 * @author choeger
 *
 */
public class Model {
	
	private List<Terminal> input;
	
	private Absy child = null;

	private ClassNode rootNode;
	private ArrayList<Position> foldablePositions;
	private IDocument document;
	
	public Model(IDocument document, List<Terminal> input, Absy rootAbsy) {
		this.document = document;
		this.child = rootAbsy;
		this.input = input;
		foldablePositions = new ArrayList<Position>();
		
		try {
			this.rootNode = AbsyToIM.buildFromAbsy((NT_Stored_Definition) rootAbsy);
			System.err.println("ROOT NODE: " + rootNode.toString());
			System.err.println("CHILDREN: " + rootNode.getChildren());
			System.err.println("ERRORS:" + AbsyToIM.getErrorList());
		} catch (Exception e) {
			e.printStackTrace();
			rootNode = null;
		}
	}

	/**
	 * @return the child
	 */
	public Absy getChild() {
		return child;
	}

	/**
	 * @param child the child to set
	 */
	public void setChild(Absy child) {
		this.child = child;
	}

	public void setInput(List<Terminal> input) {
		this.input = input;
	}

	public List<Terminal> getInput() {
		return input;
	}

	public ArrayList<Position> getAllFoldablePositions() {
		return foldablePositions;
	}

	public ClassNode getRootNode() {
		return rootNode;
	}

	/**
	 * create a foldable position from a given token range
	 * this will always include the complete last line of the region
	 * @param range
	 * @return
	 * @throws BadLocationException 
	 */
	public Position rangeToFoldablePosition(Range range) {
		int startOffset = input.get(range.getStartToken()).getStartOffset();
		int endOffset = input.get(range.getEndToken()).getEndOffset();
		try {
			final int lineIndex = document.getLineOfOffset(endOffset);
			endOffset = document.getLineOffset(lineIndex) + document.getLineLength(lineIndex);
		} catch (BadLocationException e) {}
			
		return new Position(startOffset, endOffset - startOffset);
	}

	
	/**
	 * return the length (count of characters of a given token range)
	 * @param range
	 * @return
	 */
	public int lengthOf(Range range) {
		int startOffset = input.get(range.getStartToken()).getStartOffset();
		int endOffset = input.get(range.getEndToken()).getEndOffset();
		return endOffset - startOffset;
	}
}
