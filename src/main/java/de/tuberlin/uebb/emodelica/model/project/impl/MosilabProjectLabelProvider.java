/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project.impl;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.emodelica.model.project.ILibraryContainer;
import de.tuberlin.uebb.emodelica.model.project.ILibraryEntry;
import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IMosilabEnvironment;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;

/**
 * @author choeger
 * 
 */
public class MosilabProjectLabelProvider implements ILabelProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object arg0) {
		if (arg0 instanceof ILibraryContainer) {
			return Images.LIBRARY_FOLDER_DESCRIPTOR.createImage();
		} else if (arg0 instanceof IModelicaPackage) {
			return Images.PKG_IMAGE_DESCRIPTOR.createImage();
		} else if (arg0 instanceof IMosilabSource) {
			return Images.SOURCE_FOLDER_DESCRIPTOR.createImage();
		} else if (arg0 instanceof IMosilabEnvironment) {
			return Images.LIBRARY_FOLDER_DESCRIPTOR.createImage();
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object arg0) {
		if (arg0 instanceof ILibraryContainer) {
			return ((ILibraryContainer) arg0).getName();
		} else if (arg0 instanceof IModelicaPackage) {
			return ((IModelicaPackage) arg0).getFullName();
		} else if (arg0 instanceof IMosilabSource) {
			return ((IMosilabSource) arg0).getBasePath().getName();
		} else if (arg0 instanceof ILibraryEntry) {
			return ((ILibraryEntry) arg0).getName();
		} 

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.
	 * jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang
	 * .Object, java.lang.String)
	 */
	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		System.err.println("LabelProvider.isLabelProperty");
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse
	 * .jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub

	}

}
