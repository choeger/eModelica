/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project.impl;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResourceChangedListener;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IProjectManager;

/**
 * @author choeger
 * 
 */
public class MosilabProjectContentProvider implements ITreeContentProvider, IModelicaResourceChangedListener {

	private Viewer viewer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
	 * Object)
	 */
	@Override
	public Object[] getChildren(Object arg0) {
		if (arg0 instanceof IProject) {
			IProjectManager pm = EModelicaPlugin.getDefault().getProjectManager();
			if (pm.isMosilabProject((IProject)arg0)) {
				final IMosilabProject mosilabProject = pm.getMosilabProject((IProject)arg0);
				mosilabProject.registerListener(this);
				return mosilabProject.getChildren().toArray();
			}
		}
		
		if (arg0 instanceof IModelicaResource) {
			IModelicaResource resource = (IModelicaResource)arg0;
			return resource.getChildren().toArray();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
	 * )
	 */
	@Override
	public Object getParent(Object arg0) {
		if (arg0 instanceof IMosilabProject)
			return ((IMosilabProject)arg0).getProject();
		
		if (arg0 instanceof IModelicaResource) {
			IModelicaResource resource = (IModelicaResource)arg0;
			return resource.getParent();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
	 * Object)
	 */
	@Override
	public boolean hasChildren(Object arg0) {
		if (arg0 instanceof IProject) {
			if (EModelicaPlugin.getDefault().getProjectManager().isMosilabProject((IProject)arg0))
					return true;
		}
		
		if (arg0 instanceof IModelicaResource) {
			IModelicaResource resource = (IModelicaResource)arg0;
			return !resource.getChildren().isEmpty();
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
	 * .lang.Object)
	 */
	@Override
	public Object[] getElements(Object arg0) {
		
		if (hasChildren(arg0))
			return getChildren(arg0);
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
	 * .viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object arg1, Object arg2) {
		this.viewer = viewer;
	}

	@Override
	public void resourceChanged(IModelicaResource resource) {
		if (viewer != null)
			viewer.refresh();
	}
}
