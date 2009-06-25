/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project;

import java.util.List;

/**
 * @author choeger
 * Interface for containers of multiple libraries
 */
public interface ILibraryContainer extends IModelicaResource {

	public String getName();
	
	public List<ILibraryEntry> getLibraries();

	/**
	 * add a new ILibrary entry to that container
	 * @param location
	 * @param name
	 * @param version
	 */
	public void add(String location, String name, String version);
	
}
