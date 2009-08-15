/**
 * 
 */
package de.tuberlin.uebb.emodelica.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;

import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;

/**
 * @author choeger
 *
 */
public class ResourcesToModelicaAdapterFactory implements IAdapterFactory {

	private static Map<IResource, IModelicaResource> mapping = new HashMap<IResource, IModelicaResource>();

	public static void map(IResource resource, IModelicaResource modelicaResource) {
		mapping.put(resource, modelicaResource);
	}
	
	public static void unMap(IResource resource) {
		mapping.remove(resource);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (IModelicaResource.class.isAssignableFrom(adapterType))
			if (adaptableObject instanceof IResource) {
				return mapping.get(adaptableObject);
			}
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class[] getAdapterList() {
		return new Class[] {IModelicaResource.class};
	}

}
