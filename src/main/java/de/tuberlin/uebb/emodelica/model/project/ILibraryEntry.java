/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project;

import org.eclipse.core.resources.IFolder;

/**
 * @author choeger
 *
 */
public interface ILibraryEntry extends IModelicaPackageContainer {

	/**
	 * 
	 * @return the name of that library entry (or null if none exists)
	 */
	public String getName();

	public String getLocation();
	
	public String getVersion();
}
