/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.experiments.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;
import de.tuberlin.uebb.emodelica.model.experiments.IExperimentContainer;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.impl.ModelicaResource;

/**
 * @author choeger
 *
 */
public class ExperimentContainer extends ModelicaResource implements IExperimentContainer {

	final private List<IExperiment> experiments;
	private IMosilabProject project;
	private IFolder folder;
	
	@Override
	final public List<IExperiment> getExperiments() {
		return experiments;
	}

	public ExperimentContainer(List<IExperiment> experiments,IMosilabProject project, IFolder parent) {
		super();
		this.experiments = experiments;
		this.project = project;
		setResource(parent);
	
		doRefresh();
		//Do not re-sync on construction - the adapter manager may not be loaded yet
		//syncChildren();
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
			System.err.println("mapping: " + resource + " to " + this);
			super.setResource(resource);
			folder = (IFolder) resource;
		}
	}

	@Override
	public void syncChildren() {
		experiments.clear();
		try {
			for (IResource member : folder.members())
				if (member instanceof IFile) {
					IModelicaResource exp = (IModelicaResource) member.getAdapter(IModelicaResource.class);
					
					if (exp != null && exp instanceof TextFileExperiment) {
						experiments.add((IExperiment) exp);
					}
				}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doRefresh() {
	
		try {
			for (IResource member : folder.members()) {
				if (member instanceof IFile) {
					IFile file = (IFile)member;
					IModelicaResource exp = (IModelicaResource) file.getAdapter(IModelicaResource.class);
					if (exp == null || !(exp instanceof IExperiment)) {
						exp = new TextFileExperiment(this, file);
					}
				}
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setParent(IModelicaResource newParent) {
		if (newParent instanceof IMosilabProject)
			project = (IMosilabProject) newParent;
	}
	
}
