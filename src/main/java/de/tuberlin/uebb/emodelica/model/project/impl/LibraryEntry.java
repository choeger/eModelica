/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;

import de.tuberlin.uebb.emodelica.model.project.ILibraryEntry;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;

/**
 * @author choeger
 *
 */
public class LibraryEntry extends WorkspaceModelicaPackageContainer implements ILibraryEntry  {
	
	private String name;
	private IModelicaResource parent;
	private String location;
	private String version;
	List<IModelicaResource> children = new ArrayList<IModelicaResource>();
	
	public LibraryEntry(IModelicaResource parent, String path, String name, String version) {
		super(null);
		this.parent = parent; 
		this.name = name;
		IModelicaResource res = parent;
		while(!(res instanceof IMosilabProject))
			res = res.getParent();
		
		location = path;
		this.version = version;
		
		syncChildren();
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<IModelicaResource> getChildren() {
		return children;
	}

	@Override
	public IModelicaResource getParent() {
		return parent;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public IResource getResource() {
		// TODO Check for linked resource
		return null;
	}

	@Override
	public void setParent(IModelicaResource newParent) {
		this.parent = newParent;
	}
}
