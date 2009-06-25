/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.dialogs;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

class FolderLabelProvider implements ILabelProvider {

	@Override
	public Image getImage(Object arg0) {
		if (arg0 instanceof IFolder)
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
	
		return null;
	}

	@Override
	public String getText(Object arg0) {
		if (arg0 instanceof IFolder)
			return ((IFolder)arg0).getName();
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