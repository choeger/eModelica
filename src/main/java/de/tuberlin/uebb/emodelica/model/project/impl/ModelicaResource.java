/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project.impl;

import java.util.HashSet;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.PlatformObject;

import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResourceChangedListener;
import de.tuberlin.uebb.emodelica.util.ResourcesToModelicaAdapterFactory;

/**
 * @author choeger
 *
 */
public abstract class ModelicaResource extends PlatformObject implements IModelicaResource {

	private HashSet<IModelicaResourceChangedListener> listeners = new HashSet<IModelicaResourceChangedListener>();
	public boolean dirty = false;
	
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
	
	protected boolean isDirty() {
		return dirty;
	}
	
	@Override
	public void refresh() {
		if (dirty) {
			System.err.println("[RESOURCE_REFRESH] " + " refreshing: " + this);
			doRefresh();
			notifyListeners();
			dirty = false;
			syncChildren();
			for (Object child : getChildren())
				if (child instanceof IModelicaResource)
					((IModelicaResource)child).refresh();
		}
	}
	
	protected abstract void doRefresh();
	
	@Override
	public void markAsDirty() {
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.project.IModelicaResource#setResource(org.eclipse.core.resources.IResource)
	 */
	@Override
	public void setResource(IResource resource) {
		ResourcesToModelicaAdapterFactory.unMap(getResource());
		ResourcesToModelicaAdapterFactory.map(resource, this);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		ResourcesToModelicaAdapterFactory.unMap(getResource());
		super.finalize();
	}

}
