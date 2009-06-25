/**
 * 
 */
package de.tuberlin.uebb.emodelica.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterFactory;

import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;
import de.tuberlin.uebb.emodelica.model.project.impl.WorkspaceModelicaPackageContainer;

/**
 * @author choeger
 *
 */
public class ModelicaAdapterFactory implements IAdapterFactory {

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Object arg0, Class arg1) {
	
		if (arg0 instanceof IMosilabSource) {
			IMosilabSource src = (IMosilabSource)arg0;
			if (arg1.equals(IProject.class)) {
				return ((IMosilabProject)src.getParent()).getProject();
			} else if (arg1.isAssignableFrom(IResource.class)) {
				return src.getBasePath();
			}
			return ((IAdaptable)arg0).getAdapter(arg1);
		} else if (arg0 instanceof IModelicaPackage) {
			IModelicaPackage pkg = (IModelicaPackage)arg0;
			if (arg1.equals(IProject.class)) {
				return pkg.getParent().getAdapter(arg1);
			} else if (arg1.isAssignableFrom(IResource.class)) {
				return pkg.getContainer();
			}
			return ((IAdaptable)arg0).getAdapter(arg1);
		} else return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	@Override
	public Class[] getAdapterList() {
		return new Class[] {IProject.class, IResource.class};
	}

}
