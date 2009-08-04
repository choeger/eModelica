/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project.impl;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.IPipelinedTreeContentProvider;
import org.eclipse.ui.navigator.PipelinedShapeModification;
import org.eclipse.ui.navigator.PipelinedViewerUpdate;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.experiments.IExperimentContainer;
import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResourceChangedListener;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IProjectManager;

/**
 * @author choeger
 * 
 */
public class MosilabProjectContentProvider implements IPipelinedTreeContentProvider, IModelicaResourceChangedListener {

	private Viewer viewer;
	private boolean onlyPackages;
	
	public MosilabProjectContentProvider() {
		this.onlyPackages = false;
	}

	public MosilabProjectContentProvider(boolean onlyPackages) {
		this.onlyPackages = onlyPackages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
	 * Object)
	 */
	@Override
	public Object[] getChildren(Object arg0) {
		if (arg0 instanceof IProjectManager) {
			IProjectManager pm = (IProjectManager)arg0;
			return pm.getAllMosilabProjects().toArray();
		}
		
		if (arg0 instanceof IMosilabProject) {
			IMosilabProject prj = (IMosilabProject)arg0;
			return prj.getSrcFolders().toArray();
		}
				
		if (arg0 instanceof IProject) {
			IProjectManager pm = EModelicaPlugin.getDefault().getProjectManager();
			if (pm.isMosilabProject((IProject)arg0)) {
				final IMosilabProject mosilabProject = pm.getMosilabProject((IProject)arg0);
				mosilabProject.registerListener(this);
				return mosilabProject.getChildren().toArray();
			}
		}
		
		if (arg0 instanceof IModelicaResource) {
			if (arg0 instanceof IModelicaPackage && onlyPackages)
				return null;
			IModelicaResource resource = (IModelicaResource)arg0;
			return resource.getChildren().toArray();
		}
		
		if (arg0 instanceof IExperimentContainer) {
			return ((IExperimentContainer)arg0).getExperiments().toArray();
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
		
		if (arg0 instanceof IExperimentContainer) {
			return ((IExperimentContainer)arg0).getProject();
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
			if (arg0 instanceof IModelicaPackage && onlyPackages)
				return false;
			IModelicaResource resource = (IModelicaResource)arg0;
			return !resource.getChildren().isEmpty();
		}
		
		if (arg0 instanceof IExperimentContainer) {
			return ((IExperimentContainer)arg0).getExperiments().size() > 0;
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

	@SuppressWarnings("unchecked")
	@Override
	public void getPipelinedChildren(Object parent, Set theCurrentChildren) {
		cleanAndAddOwn(getChildren(parent), theCurrentChildren);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getPipelinedElements(Object anInput, Set theCurrentElements) {
		cleanAndAddOwn(getElements(anInput), theCurrentElements);
	}

	@Override
	public Object getPipelinedParent(Object anObject, Object suggestedParent) {
		return suggestedParent;
	}

	@Override
	public PipelinedShapeModification interceptAdd(
			PipelinedShapeModification anAddModification) {
		return anAddModification;
	}

	@Override
	public boolean interceptRefresh(PipelinedViewerUpdate refreshSynchronization) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PipelinedShapeModification interceptRemove(
			PipelinedShapeModification removeModification) {
		return removeModification;
	}

	@Override
	public boolean interceptUpdate(PipelinedViewerUpdate anUpdateSynchronization) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void init(ICommonContentExtensionSite config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restoreState(IMemento memento) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void saveState(IMemento memento) {
		// TODO Auto-generated method stub
	}
	
	private void cleanAndAddOwn(Object[] children, Set<Object> theCurrentChildren) {
		for (Object child : children) {
			if (child instanceof IModelicaResource) {
				IModelicaResource modRes = (IModelicaResource)child;
				IResource resource = modRes.getResource();				
				
				if (resource != null && theCurrentChildren.contains(resource)) {
					System.err.println("removing " + resource + " because of " + modRes);
					theCurrentChildren.remove(resource);
				}
			}
			theCurrentChildren.add(child);
		}
	}
}
