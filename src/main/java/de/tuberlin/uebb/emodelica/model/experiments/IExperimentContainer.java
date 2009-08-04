/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.experiments;

import java.util.List;

import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;

/**
 * Experiment container for MOSILAB projects
 * @author choeger
 * 
 */
public interface IExperimentContainer {

	public IMosilabProject getProject();
	
	public List<IExperiment> getExperiments();
	
}
