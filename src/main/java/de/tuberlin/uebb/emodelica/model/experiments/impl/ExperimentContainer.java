/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.experiments.impl;

import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;

import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;
import de.tuberlin.uebb.emodelica.model.experiments.IExperimentContainer;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResourceChangedListener;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.impl.ModelicaResource;

/**
 * @author choeger
 *
 */
public class ExperimentContainer extends ModelicaResource implements IExperimentContainer {

	final private List<IExperiment> experiments;
	final private IMosilabProject project;
	private IFolder folder;
	
	@Override
	final public List<IExperiment> getExperiments() {
		return experiments;
	}

	public ExperimentContainer(List<IExperiment> experiments, IMosilabProject parent) {
		super();
		this.experiments = experiments;
		project = parent;
		setResource(project.getProject().getFolder(".experiments"));
	}

	@Override
	public IMosilabProject getProject() {
		return project;
	}

	@Override
	public List<? extends Object> getChildren() {
		return experiments;
	}

	@Override
	public IModelicaResource getParent() {
		return project;
	}

	@Override
	public IResource getResource() {
		return folder;
	}

	@Override
	public void setResource(IResource resource) {
		if (resource instanceof IFolder) {
			super.setResource(resource);
			folder = (IFolder) resource;
		}
	}

	@Override
	public void syncChildren() {
		/* nothing needed */		
	}

	@Override
	protected void doRefresh() {
				
	}
	
}
