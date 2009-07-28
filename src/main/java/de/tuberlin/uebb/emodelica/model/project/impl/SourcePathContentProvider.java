package de.tuberlin.uebb.emodelica.model.project.impl;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IProjectManager;

public class SourcePathContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getChildren(Object arg0) {
		if (arg0 instanceof IProjectManager) {
			IProjectManager pm = (IProjectManager)arg0;
			return pm.getAllMosilabProjects().toArray();
		} else if (arg0 instanceof IMosilabProject) {
			IMosilabProject pj = (IMosilabProject)arg0;
			return pj.getSrcFolders().toArray();
		}
		return new Object[]{};
	}

	@Override
	public Object getParent(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object arg0) {
		if (arg0 instanceof IMosilabProject)
			return ((IMosilabProject)arg0).getSrcFolders().size() > 0;
		return false;
	}

	@Override
	public Object[] getElements(Object arg0) {
		if (arg0 instanceof IProjectManager) {
			IProjectManager pm = (IProjectManager)arg0;
			return pm.getAllMosilabProjects().toArray();
		} else if (arg0 instanceof IMosilabProject) {
			IMosilabProject pj = (IMosilabProject)arg0;
			return pj.getSrcFolders().toArray();
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
