/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.experiments;

import java.util.Date;
import java.util.List;

import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;

/**
 * Common interface to access experiment data from MOSILAB experiments
 * @author choeger
 *
 */
public interface IExperiment {

	/**
	 * The parent project
	 * @return The project that Experiment was run in
	 */
	public IMosilabProject getParentProject();
	
	/**
	 * The time that experiment was run
	 * @return The experiment time
	 */
	public Date getDate();
	
	/**
	 * Name of the experiment
	 * @return a unique name for the given experiment
	 */
	public String getName();
	
	/**
	 * All curves of the given experiment
	 * @return
	 */
	public List<ICurve> getCurves();

}
