/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project;

/**
 * @author choeger
 *
 */
public interface IModelicaResourceChangedListener {

	/**
	 * Notify this listener that the given resource has changed
	 * @param resource
	 */
	public void resourceChanged(IModelicaResource resource);
}
