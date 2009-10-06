package de.tuberlin.uebb.emodelica.model;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.modelica.im.impl.nodes.ClassNode;
import de.tuberlin.uebb.modelica.im.nodes.EClassType;
import de.tuberlin.uebb.modelica.im.nodes.ENodeFlags;
import de.tuberlin.uebb.modelica.im.nodes.IVarDefNode;


public class ModelLabelProvider extends LabelProvider {

	public String getText(Object obj) {
		return obj.toString() + "[" + obj.getClass().getSimpleName() + "]";
	}
	public Image getImage(Object obj) {
		if (obj instanceof IVarDefNode) {
			IVarDefNode node = (IVarDefNode)obj;
			if (node.getFlags().contains(ENodeFlags.PROTECTED))
				return Images.PRIVATE_FIELD_DESCRIPTOR.createImage();
			else
				return Images.PUBLIC_FIELD_DESCRIPTOR.createImage();
		}
		
		if (obj instanceof ClassNode) {
			ClassNode classNode = (ClassNode)obj;
			if (classNode.getClassType().equals(EClassType.CLASS))
				return Images.CLASS_IMAGE_DESCRIPTOR.createImage();
			if (classNode.getClassType().equals(EClassType.PACKAGE))
				return Images.PKG_IMAGE_DESCRIPTOR.createImage();			
		}
		
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
}

