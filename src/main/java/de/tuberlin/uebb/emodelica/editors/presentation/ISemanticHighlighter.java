/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors.presentation;

import java.util.Collection;

import de.tuberlin.uebb.modelica.im.nodes.INode;

/**
 * @author choeger
 *
 */
public interface ISemanticHighlighter {
	
	/**
	 * This method is invoked for every changed INode in the model</br>
	 * The intention is to allow the Highlighter to collect informations about its context.
	 * @param node The node to handle
	 * @return true if the Highlighter needs to handle subsequent children nodes
	 */
	public boolean handleNode(INode node);
	
	/**
	 * Informs this Highlighter that the node (and all ist children) has been handled
	 * @param node The node that was handled
	 */
	public void nodeHandled(INode node);
	
	/**
	 * This method is invoked after all nodes have been handled
	 * @return All HighlightingPositions created by this highlighter
	 */
	public Collection<HighlightingPosition> getHighlightingPositions();
}
