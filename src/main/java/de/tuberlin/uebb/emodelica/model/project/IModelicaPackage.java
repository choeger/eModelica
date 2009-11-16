package de.tuberlin.uebb.emodelica.model.project;

import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;

/**
 * 
 * @author choeger
 * This interface represents a Modelica package
 */
public interface IModelicaPackage extends IModelicaResource {

	/**
	 * 
	 * @return the full name of that package
	 */
	public String getFullName();
	
	/**
	 * 	
	 * @return all source Files in that package
	 */
	public List<IFile> getContents();
	
	//TODO: add documentation and package meta data

	/**
	 * @return true if that package contains only Modelica resources
	 */
	public boolean hasOnlyModelicaResources();

	/**
	 * get the container to that package
	 * @return
	 */
	public IContainer getContainer();

	public String getDocumentation();
}
