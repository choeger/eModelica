package de.tuberlin.uebb.emodelica.model;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.tuberlin.uebb.emodelica.model.dom.DOMNode;
import de.tuberlin.uebb.emodelica.model.dom.DOMRootNode;


public class ModelLabelProvider extends LabelProvider {

	public String getText(Object obj) {
		return obj.toString();
	}
	public Image getImage(Object obj) {
		if (obj instanceof DOMRootNode)
			return null;
		
		if (obj instanceof DOMNode) {
			ImageDescriptor imageDescriptor = ((DOMNode)obj).getImageDescriptor();
			if (imageDescriptor == null) {
				System.err.println(((DOMNode)obj).getValue());
				System.err.println(obj);
			}
			return imageDescriptor.createImage();
		}
		
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
}

