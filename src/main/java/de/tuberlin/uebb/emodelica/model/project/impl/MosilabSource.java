/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project.impl;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;

import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;
import de.tuberlin.uebb.emodelica.util.ModelicaToResourcesAdapterFactory;
import de.tuberlin.uebb.emodelica.util.ResourcesToModelicaAdapterFactory;

/**
 * @author choeger
 *
 */
public class MosilabSource extends WorkspaceModelicaPackageContainer implements
		IMosilabSource {

	private boolean isRoot;
	private IMosilabProject parent;

	public MosilabSource(IMosilabProject parent, IContainer container) {
		super(container);
		this.parent = parent;
		this.isRoot = container.getType() == IResource.PROJECT;
	}

	@Override
	public boolean isRoot() {
		return isRoot;
	}

	@Override
	public IFolder getBasePath() {
		if (isRoot) return null;
		else if (getResource() instanceof IFolder)
			return (IFolder)getResource();
		else return null;
	}

	@Override
	public IModelicaResource getParent() {
		return parent;
	}
	
	@Override
	public String toString() {
		if (isRoot)
			return ".";
		else
			return getResource().getProjectRelativePath().toString();
	}

	@Override
	public void setParent(IModelicaResource newParent) {
		if (newParent instanceof IMosilabProject)
			parent = (IMosilabProject) newParent;
	}
}
