/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.experiments;

import org.eclipse.ui.IViewPart;

/**
 * @author choeger
 *
 */
public interface IExperimentView extends IViewPart {

	/**
	 * Set the experiment for that IExperimentView
	 * @param experiment the experiment to show
	 */
	public void setExperiment(IExperiment experiment);
}
