package de.tuberlin.uebb.emodelica.model;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.tuberlin.uebb.emodelica.Images;


public class ModelLabelProvider extends LabelProvider {

	public String getText(Object obj) {
		return obj.toString() + "[" + obj.getClass().getSimpleName() + "] @" + Integer.toHexString(System.identityHashCode(obj));
	}
	public Image getImage(Object obj) {
		Image im = Images.getImageForModel(obj);
		if (im != null)
			return im;
		
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
}

