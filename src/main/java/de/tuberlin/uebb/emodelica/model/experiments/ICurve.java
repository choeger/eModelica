/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.experiments;

import java.util.List;

/**
 * Interface for a single curve from a MOSILAB experiment
 * (index based mapping to all other curves of the experiment)
 * @author choeger
 *
 */
public interface ICurve {
	
	/**
	 * get the name of the variable that is represented by the data
	 * @return name of the variable
	 */
	public String getVariableName();

	/**
	 * set the data of that curve
	 * @param points the new data
	 */
	public void setPoints(List<Double> points);
	
	/**
	 * get the data of that curve
	 * This data is mapped by index to other curves
	 * @return the list of all points (index based)
	 */
	public List<Double> getPoints();

	/**
	 * get the experiment that curve belongs to
	 * @return The experiment that delivered the data
	 */
	public IExperiment getExperiment();
}
