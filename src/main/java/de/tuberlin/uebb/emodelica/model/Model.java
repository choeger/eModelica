/** 
 * Test
 */
package de.tuberlin.uebb.emodelica.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;

import de.tuberlin.uebb.modelica.im.ILocation;
import de.tuberlin.uebb.modelica.im.impl.Location;
import de.tuberlin.uebb.modelica.im.impl.generated.moparser.NT_Stored_Definition;
import de.tuberlin.uebb.modelica.im.impl.generator.AbsyToIM;
import de.tuberlin.uebb.modelica.im.impl.mappings.nodes.StoredDefinitionMapping;
import de.tuberlin.uebb.modelica.im.impl.nodes.StoredDefinitionNode;
import de.tuberlin.uebb.modelica.im.nodes.IClassNode;
import de.tuberlin.uebb.modelica.im.nodes.INode;
import de.tuberlin.uebb.page.grammar.symbols.Terminal;
import de.tuberlin.uebb.page.grammar.symbols.TerminalEOF;
import de.tuberlin.uebb.page.lexer.ILexer;
import de.tuberlin.uebb.page.parser.symbols.IAbsy;
import de.tuberlin.uebb.page.parser.util.Range;

/**
 * @author choeger
 *
 */
public class Model {
	
	private List<Terminal> input;

	private IAbsy child = null;
	
	private StoredDefinitionNode rootNode;
	private ArrayList<Position> foldablePositions;
	private IDocument document;

	private boolean shouldCompact = true;
	private boolean compacted;
	
	public Model(IDocument document, ILexer lexer, IAbsy rootAbsy) {
		this.document = document;
		this.child = rootAbsy;
		this.input = lexer.getCachedInput();
		foldablePositions = new ArrayList<Position>();
		
		if (rootAbsy != null)
			updateIM(lexer, rootAbsy);
		compacted=false;
	}

	private void updateIM(ILexer lexer, IAbsy rootAbsy) {
		try {
			StoredDefinitionMapping mapping = (StoredDefinitionMapping) rootAbsy.adapt(StoredDefinitionMapping.class);
			if (mapping == null) {
				mapping = new StoredDefinitionMapping();
				mapping.init((NT_Stored_Definition) rootAbsy);
			}
			
			mapping.update();
			rootNode = mapping.getResultIM();
			
			
//			for (INode node : rootNode.getChildren().values())
//				if (node instanceof ClassNode)
//				Flattening.flatten((ClassNode)node);
			
//			System.err.println("ROOT NODE: " + rootNode.toString());
//			System.err.println("CHILDREN: " + rootNode.getChildren());
//			System.err.println("ERRORS:" + AbsyToIM.getErrorList());
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

	public StoredDefinitionNode getRootNode() {
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

	public Terminal getTerminalBefore(int offset) {
	
		TerminalEOF searchTerm = new TerminalEOF();
		searchTerm.setEndOffset(offset);
		searchTerm.setStartOffset(offset);
		
		int i = Collections.binarySearch(input, searchTerm, new Comparator<Terminal>(){

			@Override
			public int compare(Terminal term1, Terminal term2) {
				return term1.getEndOffset() - term2.getEndOffset();
			}});
		
		if (i < 0)
			return null;
		return input.get(i);
	}

	public void getEnclosingLexicalNodes(int offset, INode startNode, List<IClassNode> enclosingNodes) {
		for (String childName : startNode.getChildrenInOrder()) {
			INode child = startNode.getChild(childName);
			if (child instanceof IClassNode) {
				if (child.getStartOffset() <= offset && offset < child.getEndOffset()) {
					enclosingNodes.add((IClassNode) child);
					getEnclosingLexicalNodes(offset, child, enclosingNodes);
				}
			}
		}
	}
	
	public void compact() {
		if (!compacted) {
			this.input=null;
			this.child=null;
			this.document = null;
			compacted=true;
		}
	}
	
	public boolean isCompacted() {
		return compacted;
	}
	
	public boolean shouldCompact() {
		return shouldCompact;
	}
	
	public void setToCompact(boolean shouldCompact) {
		this.shouldCompact = shouldCompact; 
	}
}
