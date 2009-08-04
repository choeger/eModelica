/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.experiments.impl;

import java.util.List;

import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;
import de.tuberlin.uebb.emodelica.model.experiments.IExperimentContainer;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;

/**
 * @author choeger
 *
 */
public class ExperimentContainer implements IExperimentContainer {

	final private List<IExperiment> experiments;
	final private IMosilabProject project;
	
	@Override
	final public List<IExperiment> getExperiments() {
		return experiments;
	}

	public ExperimentContainer(List<IExperiment> experiments, IMosilabProject parent) {
		super();
		this.experiments = experiments;
		project = parent;
	}

	@Override
	public IMosilabProject getProject() {
		return project;
	}
	
}
