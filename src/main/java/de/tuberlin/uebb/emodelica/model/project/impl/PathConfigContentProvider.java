package de.tuberlin.uebb.emodelica.model.project.impl;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.ui.SourceFolderSelectionView.SourcesOnlyContainer;

public class PathConfigContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getChildren(Object arg0) {
		if (arg0 instanceof SourcesOnlyContainer) {
			return ((SourcesOnlyContainer)arg0).getChildren().toArray();
		} 
		return new Object[]{};
	}

	@Override
	public Object getParent(Object arg0) {
		if (arg0 instanceof IModelicaResource)
			return ((IModelicaResource)arg0).getParent();
		return null;
	}

	@Override
	public boolean hasChildren(Object arg0) {
		if (arg0 instanceof SourcesOnlyContainer) {
			return ((SourcesOnlyContainer)arg0).getChildren().size() > 0;
		}
		return false;
	}

	@Override
	public Object[] getElements(Object arg0) {
		if (arg0 instanceof SourcesOnlyContainer) {
			return ((SourcesOnlyContainer)arg0).getChildren().toArray();
		} 
		return new Object[] {};
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
	}
}
