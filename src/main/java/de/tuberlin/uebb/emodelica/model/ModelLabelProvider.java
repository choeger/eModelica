package de.tuberlin.uebb.emodelica.model;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.modelica.im.ClassNode;
import de.tuberlin.uebb.modelica.im.ClassNode.ClassType;


public class ModelLabelProvider extends LabelProvider {

	public String getText(Object obj) {
		return obj.toString();
	}
	public Image getImage(Object obj) {
		
		if (obj instanceof ClassNode) {
			ClassNode classNode = (ClassNode)obj;
			if (classNode.getClassType().equals(ClassType.CLASS_TYPE_CLASS))
				return Images.CLASS_IMAGE_DESCRIPTOR.createImage();
			if (classNode.getClassType().equals(ClassType.CLASS_TYPE_PACKAGE))
				return Images.PKG_IMAGE_DESCRIPTOR.createImage();			
		}
		
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
}

