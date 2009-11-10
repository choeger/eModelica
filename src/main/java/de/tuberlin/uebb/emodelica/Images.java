/**
 * 
 */
package de.tuberlin.uebb.emodelica;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import de.tuberlin.uebb.modelica.im.impl.nodes.ClassNode;
import de.tuberlin.uebb.modelica.im.nodes.EClassType;
import de.tuberlin.uebb.modelica.im.nodes.ENodeFlags;
import de.tuberlin.uebb.modelica.im.nodes.IImportNode;
import de.tuberlin.uebb.modelica.im.nodes.IImports;
import de.tuberlin.uebb.modelica.im.nodes.IVarDefNode;
import de.tuberlin.uebb.modelica.im.nodes.IWithinStatement;

/**
 * @author choeger
 *
 */
public class Images {
	public static final ImageDescriptor REFRESH_IMAGE_DESCRIPTOR = 
		ImageDescriptor.createFromFile(Images.class, "/icons/refresh.gif");
	public static final ImageDescriptor WITHIN_PKG_IMAGE_DESCRIPTOR = 
		ImageDescriptor.createFromFile(Images.class, "/icons/packd_obj.gif");
	public static final ImageDescriptor PKG_IMAGE_DESCRIPTOR =
		ImageDescriptor.createFromFile(Images.class, "/icons/package_obj.gif");	
	public static final ImageDescriptor CLASS_IMAGE_DESCRIPTOR =
		ImageDescriptor.createFromFile(Images.class, "/icons/class_obj.gif");	
	public static final ImageDescriptor PLUGIN_IMAGE_DESCRIPTOR =
		ImageDescriptor.createFromFile(Images.class, "/icons/plugin.gif");
	public static final ImageDescriptor MODEL_IMAGE_DESCRIPTOR =
		ImageDescriptor.createFromFile(Images.class, "/icons/model.png");
	public static final ImageDescriptor SINGLE_IMPORT_IMAGE_DESCRIPTOR =
		ImageDescriptor.createFromFile(Images.class, "/icons/imp_obj.gif");
	public static final ImageDescriptor IMPORT_IMAGE_DESCRIPTOR =
		ImageDescriptor.createFromFile(Images.class, "/icons/impc_obj.gif");
	public static final ImageDescriptor PRIVATE_FIELD_DESCRIPTOR =
		ImageDescriptor.createFromFile(Images.class, "/icons/field_private_obj.gif");
	public static final ImageDescriptor PUBLIC_FIELD_DESCRIPTOR =
		ImageDescriptor.createFromFile(Images.class, "/icons/public_object.gif");
	public static final ImageDescriptor SOURCE_FOLDER_DESCRIPTOR =
		ImageDescriptor.createFromFile(Images.class, "/icons/packagefolder_obj.gif");
	public static final ImageDescriptor LIBRARY_FOLDER_DESCRIPTOR =
		ImageDescriptor.createFromFile(Images.class, "/icons/library_obj.gif");
	public static final ImageDescriptor CHART_DESCRIPTOR =
		ImageDescriptor.createFromFile(Images.class, "/icons/chart.png");
	public static final ImageDescriptor EXPERIMENT_DESCRIPTOR =
		ImageDescriptor.createFromFile(Images.class, "/icons/experiment.png");
	public static final ImageDescriptor EXPERIMENTS_DESCRIPTOR =
		ImageDescriptor.createFromFile(Images.class, "/icons/experiments.png");
	public static final ImageDescriptor FINAL_OVERLAY =
		ImageDescriptor.createFromFile(Images.class, "/icons/final_co.gif");
	
	public static final Image getImageForModel(Object obj) {
		if (obj instanceof IVarDefNode) {
			IVarDefNode node = (IVarDefNode)obj;
			if (node.getFlags().contains(ENodeFlags.PROTECTED))
				return PRIVATE_FIELD_DESCRIPTOR.createImage();
			else
				return PUBLIC_FIELD_DESCRIPTOR.createImage();
		}
		
		if (obj instanceof IWithinStatement) {
			return WITHIN_PKG_IMAGE_DESCRIPTOR.createImage();
		}
		
		if (obj instanceof IImports) {
			return IMPORT_IMAGE_DESCRIPTOR.createImage();
		}
		
		if (obj instanceof IImportNode) {
			return SINGLE_IMPORT_IMAGE_DESCRIPTOR.createImage();
		}
		
		if (obj instanceof ClassNode) {
			ClassNode classNode = (ClassNode)obj;
			if (classNode.getClassType().equals(EClassType.CLASS))
				return CLASS_IMAGE_DESCRIPTOR.createImage();
			if (classNode.getClassType().equals(EClassType.PACKAGE))
				return PKG_IMAGE_DESCRIPTOR.createImage();
			if (classNode.getClassType().equals(EClassType.MODEL))
				return MODEL_IMAGE_DESCRIPTOR.createImage();
		}
		return null;
	}
	

}
