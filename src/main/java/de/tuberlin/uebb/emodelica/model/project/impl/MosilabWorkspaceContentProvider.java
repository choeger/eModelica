package de.tuberlin.uebb.emodelica.model.project.impl;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IProjectManager;

public class MosilabWorkspaceContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getChildren(Object arg0) {
		if (arg0 instanceof IProjectManager) {
			IProjectManager pm = (IProjectManager)arg0;
			return pm.getAllMosilabProjects().toArray();
		}
		return new Object[]{};
	}

	@Override
	public Object getParent(Object arg0) {
		return null;
	}

	@Override
	public boolean hasChildren(Object arg0) {
		if (arg0 instanceof IProjectManager) {
			IProjectManager pm = (IProjectManager)arg0;
			return pm.getAllMosilabProjects().size() > 0;
		}
		return false;
	}

	@Override
	public Object[] getElements(Object arg0) {
		if (arg0 instanceof IProjectManager) {
			IProjectManager pm = (IProjectManager)arg0;
			return pm.getAllMosilabProjects().toArray();
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
