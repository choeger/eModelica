/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project.impl;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;

/**
 * @author choeger
 *
 */
public class DoubleEntryFilter extends ViewerFilter {

	public static final QualifiedName VISITED_BY_NAVIGATOR = new QualifiedName(null,"de.tuberlin.uebb.emodelica.visitedByNavigator");
	
	/**
	 * 
	 */
	public DoubleEntryFilter() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer arg0, Object arg1, Object arg2) {
		if (arg1 instanceof TreePath && arg2 instanceof IResource) {
			TreePath tp = (TreePath)arg1;
			IResource res = (IResource)arg2;
			try {
				Object parent = tp.getLastSegment();
				return (parent instanceof IModelicaResource || res.getSessionProperty(VISITED_BY_NAVIGATOR) == null);
			} catch (CoreException e) {
				e.printStackTrace();
				return true;
			}
		} 
		return true;
	}

}
