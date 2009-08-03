/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;

import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;

/**
 * @author choeger
 *
 */
public class ModelicaPackage extends ModelicaResource  implements IModelicaPackage, IAdaptable {

	private String fullName;
	private ArrayList<IFile> children;
	private IContainer completePath;
	private IModelicaResource parent;
	private boolean onlyModelicaResources = false;
	
	public ModelicaPackage(IModelicaResource parent, IContainer parentDir, IContainer completePath) {
		this.parent = parent;
		this.completePath = completePath;
		
		IPath suffix = completePath.getFullPath().removeFirstSegments(parentDir.getFullPath().segmentCount());
		this.fullName = suffix.toString().replace(IPath.SEPARATOR, '.');
		children = new ArrayList<IFile>();
		syncChildren();
	}
	
	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.project.IModelicaPackage#getContents()
	 */
	@Override
	public List<IFile> getContents() {
		return children;
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.model.project.IModelicaPackage#getFullName()
	 */
	@Override
	public String getFullName() {
		return fullName;
	}

	@Override
	public List<? extends Object> getChildren() {
		return children;
	}

	@Override
	public IModelicaResource getParent() {
		return parent;
	}

	@Override
	public void syncChildren() {
		children.clear();
		try {
			onlyModelicaResources = true;
			for (IResource member : completePath.members()) {
				if (member.getType() == IResource.FILE && member.getName().endsWith(".mo") && 
						!member.getName().equals("package.mo")) {
					children.add((IFile)member);
				} else onlyModelicaResources = false;	
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}	}

	@Override
	public boolean hasOnlyModelicaResources() {
		return onlyModelicaResources;
	}

	@Override
	public Object getAdapter(Class arg0) {
		
		if (arg0.equals(IResource.class)) {
			return completePath;
		}
		
		return null;
	}

	@Override
	public IContainer getContainer() {
		return completePath;
	}

	@Override
	public IResource getResource() {
		return completePath;
	}

}
