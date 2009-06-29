/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;

import de.tuberlin.uebb.emodelica.model.IModelChangedListener;

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
}
