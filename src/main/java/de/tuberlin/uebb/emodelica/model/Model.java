/** 
 * Test
 */
package de.tuberlin.uebb.emodelica.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;

import de.tuberlin.uebb.modelica.im.impl.generated.moparser.NT_Stored_Definition;
import de.tuberlin.uebb.modelica.im.impl.generator.AbsyToIM;
import de.tuberlin.uebb.modelica.im.impl.mappings.nodes.StoredDefinitionMapping;
import de.tuberlin.uebb.modelica.im.impl.nodes.ClassNode;
import de.tuberlin.uebb.page.grammar.symbols.Terminal;
import de.tuberlin.uebb.page.lexer.ILexer;
import de.tuberlin.uebb.page.parser.symbols.Absy;
import de.tuberlin.uebb.page.parser.symbols.IAbsy;
import de.tuberlin.uebb.page.parser.util.Range;

/**
 * @author choeger
 *
 */
public class Model {
	
	private List<Terminal> input;
	
	private IAbsy child = null;

	private ClassNode rootNode;
	private ArrayList<Position> foldablePositions;
	private IDocument document;
	
	public Model(IDocument document, ILexer lexer, IAbsy rootAbsy) {
		this.document = document;
		this.child = rootAbsy;
		this.input = lexer.getCachedInput();
		foldablePositions = new ArrayList<Position>();
		
		updateIM(lexer, rootAbsy);
	}

	private void updateIM(ILexer lexer, IAbsy rootAbsy) {
		try {
			StoredDefinitionMapping mapping = (StoredDefinitionMapping) rootAbsy.adapt(StoredDefinitionMapping.class);
			if (mapping == null) {
				mapping = new StoredDefinitionMapping();
				mapping.init((NT_Stored_Definition) rootAbsy);
				rootAbsy.addAdapter(StoredDefinitionMapping.class, mapping);
			}
			mapping.update();
			rootNode = mapping.getResultIM();
			
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
	public IAbsy getChild() {
		return child;
	}

	/**
	 * @param child the new Absy
	 */
	public void updateFromAbsy(IAbsy child, ILexer lexer) {
		this.child = child;
		updateIM(lexer, child);
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
