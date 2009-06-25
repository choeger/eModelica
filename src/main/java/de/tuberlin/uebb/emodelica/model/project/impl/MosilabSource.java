/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;

import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;

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

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.project.IMosilabSource#getBasePath()
	 */
	@Override
	public IContainer getContainer() {
		return container;
	}

	@Override
	public boolean isRoot() {
		return isRoot;
	}

	@Override
	public List<IFile> getContent() {
		return children;
	}

	@Override
	public IFolder getBasePath() {
		if (isRoot) return null;
		else return (IFolder)container;
	}

	@Override
	public List<? extends Object> getChildren() {
		List<Object> children = new ArrayList<Object>();
		children.addAll(this.packages);
		children.addAll(this.children);
		return children;
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
			return container.getProjectRelativePath().toString();
	}
}
