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
import org.eclipse.core.runtime.PlatformObject;

import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;

/**
 * @author choeger
 * This abstract class' purpose is to serve a getPackages() method for all classes that contain packages in a given folder
 */
public abstract class WorkspaceModelicaPackageContainer extends ModelicaResource implements IModelicaResource {

	protected ArrayList<IModelicaPackage> packages;
	protected IContainer container;
	protected List<IFile> children;
	
	public WorkspaceModelicaPackageContainer() {}
	
	public WorkspaceModelicaPackageContainer(IContainer container) {
		packages = new ArrayList<IModelicaPackage>();
		children = new ArrayList<IFile>();
		this.container = container;
		syncChildren();
	}

	/**
	 * read package structure from filesystem
	 */
	private void syncPackages() {
		packages.clear();
		recFind(container);
		System.err.println("got " + packages.size() + " packages.");
	}
	
	private void recFind(IContainer container) {
		if (container != null) {
			System.err.println("Scanning: " + container.getFullPath());
			try {
				boolean onlyModelicaResources = true;
				for (IResource resource : container.members())
					if (resource.getType() == IResource.FOLDER) {
						recFind((IFolder) resource);
						onlyModelicaResources &= resource.getSessionProperty(DoubleEntryFilter.VISITED_BY_NAVIGATOR) != null;
					} else {
						if (resource.getType() == IResource.FILE && resource.getName().equals("package.mo")) {
							ModelicaPackage pkg = new ModelicaPackage(this, this.container, container);
							packages.add(pkg);
							if (pkg.hasOnlyModelicaResources())
								container.setSessionProperty(DoubleEntryFilter.VISITED_BY_NAVIGATOR, true);
						}
					}
				if (onlyModelicaResources)
					container.setSessionProperty(DoubleEntryFilter.VISITED_BY_NAVIGATOR, true);
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
		syncPackages();
		children.clear();
		try {
			if (container != null)
			for (IResource member : container.members())
				if (member.getType() == IResource.FILE && member.getName().endsWith(".mo"))
					children.add((IFile)member);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	public IContainer getContainer() {
		return container;
	}
}
