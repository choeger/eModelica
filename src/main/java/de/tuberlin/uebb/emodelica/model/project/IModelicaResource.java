/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project;

import java.util.List;

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
}
