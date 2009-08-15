/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;

/**
 * @author choeger
 *
 */
public interface IModelicaResource extends IAdaptable {

	/**
	 * 
	 * @return the parent of that IModelicaResource
	 */
	public IModelicaResource getParent();
	
	/**
	 * 
	 * @return all children of that IModelicaResource
	 */
	public List<? extends Object> getChildren();
	
	/**
	 * let this IModelicaResource recalculate its children 
	 */
	public void syncChildren();
	
	/**
	 * register a listener to be notified on changes
	 * @param listener the new listener
	 */
	public void registerListener(IModelicaResourceChangedListener listener);
	
	/**
	 * remove a listener
	 * @param listener
	 */
	public void removeListener(IModelicaResourceChangedListener listener);
	
	/**
	 * 
	 * @return an eclipse resource that defines this IModelicaResource or null if none
	 */
	public IResource getResource();
	
	/**
	 * Set the resource that defines this IModelicaResource
	 * @param resource
	 */
	public void setResource(IResource resource);
	
	/**
	 * refresh the IModelicaResource
	 */
	public void refresh();

	/**
	 * mark this resource as needing a refresh()
	 */
	public void markAsDirty();
}
