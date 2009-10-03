/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project.impl;

import java.text.Collator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;

/**
 * @author choeger
 *
 */
public class ProjectContentSorter extends ViewerSorter {

	/**
	 * 
	 */
	public ProjectContentSorter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param collator
	 */
	public ProjectContentSorter(Collator collator) {
		super(collator);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		
		if (e1 instanceof IExperiment && e2 instanceof IExperiment)
			return ((IExperiment)e1).getDate().compareTo(((IExperiment)e2).getDate());
		
		return super.compare(viewer, e1, e2);
	}

	
	
}
