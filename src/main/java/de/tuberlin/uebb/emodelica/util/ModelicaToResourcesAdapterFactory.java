/**
 * 
 */
package de.tuberlin.uebb.emodelica.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterFactory;

import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;

/**
 * @author choeger
 *
 */
public class ModelicaToResourcesAdapterFactory implements IAdapterFactory {

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Object arg0, Class arg1) {
	
		if (arg0 instanceof IModelicaResource) {
			IModelicaResource resource = (IModelicaResource)arg0;
			if (arg1.equals(IProject.class)) {
				return resource.getResource().getProject();
			} else if (arg1.isAssignableFrom(IResource.class)) {
				return resource.getResource();
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
