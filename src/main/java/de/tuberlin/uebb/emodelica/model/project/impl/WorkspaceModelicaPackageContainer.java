/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;

/**
 * @author choeger
 * This abstract class' purpose is to serve a getPackages() method for all classes that contain packages in a given folder
 */
public abstract class WorkspaceModelicaPackageContainer extends ModelicaResource implements IModelicaResource {

	private ArrayList<IModelicaPackage> packages;
	private IContainer container;
	private List<Object> children;
	private List<IResource> contents;
	
	public WorkspaceModelicaPackageContainer() {}
	
	public WorkspaceModelicaPackageContainer(IContainer container) {
		packages = new ArrayList<IModelicaPackage>();
		contents = new ArrayList<IResource>();
		children = new ArrayList<Object>();
		setResource(container);
		
		doRefresh();
		syncChildren();
	}

	/**
	 * read package structure from filesystem
	 */
	private void refreshPackages() {
		packages.clear();
		recFind(container);
		System.err.println("got " + packages.size() + " packages.");
	}
	
	private void recFind(IContainer container) {
		if (container != null) {
			System.err.println("Scanning: " + container.getFullPath());
			try {
				for (IResource resource : container.members())
					if (resource.getType() == IResource.FOLDER) {
						IModelicaResource modRes = (IModelicaResource) resource.getAdapter(IModelicaResource.class);
						if (modRes != null && modRes instanceof IModelicaPackage)
							packages.add((IModelicaPackage) modRes);
						else recFind((IFolder) resource);
					} else {
						if (resource.getType() == IResource.FILE && resource.getName().equals("package.mo")) {
							ModelicaPackage pkg = new ModelicaPackage(this, this.container, container);
							packages.add(pkg);
						}
					}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<IModelicaPackage> getPackages() {
		return packages;
	}
	
	@Override
	public void syncChildren() {
		children.clear();
		children.addAll(packages);
		children.addAll(contents);
	}
	
	private void refreshContents() {
		contents.clear();
		try {
			if (container != null)
			for (IResource member : container.members())
				if (member.getType() == IResource.FILE && member.getName().endsWith(".mo"))
					contents.add((IFile)member);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	public IContainer getContainer() {
		return container;
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.project.impl.ModelicaResource#doRefresh()
	 */
	@Override
	protected void doRefresh() {
		refreshPackages();
		refreshContents();
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.project.IModelicaResource#getChildren()
	 */
	@Override
	public List<? extends Object> getChildren() {
		return children;
	}


	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.project.IModelicaResource#getResource()
	 */
	@Override
	public IResource getResource() {
		return container;
	}
	
	public List<IResource> getContent() {
		return contents;
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.project.impl.ModelicaResource#setResource(org.eclipse.core.resources.IResource)
	 */
	@Override
	public void setResource(IResource resource) {
		if (resource instanceof IContainer) {
			super.setResource(resource);
			container = (IContainer) resource;
		}
	}
}
