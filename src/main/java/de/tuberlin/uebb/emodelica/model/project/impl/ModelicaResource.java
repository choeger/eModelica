/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project.impl;

import java.util.HashSet;

import org.eclipse.core.runtime.PlatformObject;

import de.tuberlin.uebb.emodelica.model.IModelChangedListener;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResourceChangedListener;

/**
 * @author choeger
 *
 */
public abstract class ModelicaResource extends PlatformObject implements IModelicaResource {

	private HashSet<IModelicaResourceChangedListener> listeners = new HashSet<IModelicaResourceChangedListener>();
	
	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.project.IModelicaResource#registerListener(de.tuberlin.uebb.emodelica.model.IModelChangedListener)
	 */
	@Override
	public void registerListener(IModelicaResourceChangedListener listener) {
		listeners.add(listener);		
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.project.IModelicaResource#removeListener(de.tuberlin.uebb.emodelica.model.IModelChangedListener)
	 */
	@Override
	public void removeListener(IModelicaResourceChangedListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * notifies all registered listeners by invoking their 
	 * {@link IModelicaResourceChangedListener#resourceChanged resourceChanged()} method
	 */
	protected void notifyListeners() {
		for (IModelicaResourceChangedListener listener : listeners)
			listener.resourceChanged(this);
	}

}
