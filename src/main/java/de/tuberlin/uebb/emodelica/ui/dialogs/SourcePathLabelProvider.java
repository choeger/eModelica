package de.tuberlin.uebb.emodelica.ui.dialogs;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE.SharedImages;

import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;

public class SourcePathLabelProvider implements ILabelProvider {

	@Override
	public Image getImage(Object arg0) {
		if (arg0 instanceof IMosilabSource) {
			return Images.SOURCE_FOLDER_DESCRIPTOR.createImage();
		} else if (arg0 instanceof IMosilabProject) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(SharedImages.IMG_OBJ_PROJECT);
		}
		
		return null;
	}

	@Override
	public String getText(Object arg0) {
		if (arg0 instanceof IMosilabSource) {
			return ((IMosilabSource)arg0).toString();
		} else if (arg0 instanceof IMosilabProject) {
			return ((IMosilabProject)arg0).getProject().getName();
		}
		
		return null;
	}

	@Override
	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
