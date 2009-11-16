package de.tuberlin.uebb.emodelica.model.project;

import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;

public interface IMosilabSource extends IModelicaPackageContainer, IModelicaResource {

	/**
	 * 
	 * @return the base path of that source folder
	 */
	public IFolder getBasePath();
	
	/**
	 * 
	 * @return true if that source folder is the same as its parent (e.g. ".")
	 */
	public boolean isRoot();

	/**
	 * 
	 * @return the list of (top-level) source files in this source folder
	 */
	public List<IResource> getContents();
	
	//TODO: put source preferences in here
}
