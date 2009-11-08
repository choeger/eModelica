/**
 * 
 */
package de.tuberlin.uebb.emodelica.model;

import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.modelica.im.nodes.ENodeFlags;
import de.tuberlin.uebb.modelica.im.nodes.INode;

/**
 * @author choeger
 *
 */
public class ModelDecorator implements ILightweightLabelDecorator {

	@Override
	public void decorate(Object element, IDecoration decoration) {
		
		if (element instanceof INode) {
			final INode node = (INode) element;
			if (node.getFlags().contains(ENodeFlags.FINAL)) {
				decoration.addOverlay(Images.FINAL_OVERLAY, IDecoration.TOP_RIGHT);
			}
		}
		
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}


}
