/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.QualifiedName;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;

/**
 * @author choeger
 * Interface for projects with the MOSILAB nature. All MOSILAB stuff goes here.
 */
public interface IMosilabProject extends IModelicaResource {

	/**
	 * key for MOSILAB installation property
	 */
	public static final QualifiedName MOSILAB_ENVIRONMENT_KEY = new QualifiedName(EModelicaPlugin.PLUGIN_ID, "properties.keys.mosilab_environment");
	/**
	 * key for output folder
	 */
	public static final QualifiedName MOSILAB_OUTFOLDER_KEY = new QualifiedName(EModelicaPlugin.PLUGIN_ID, "properties.keys.mosilab_outfolder");
	
	/**
	 * Get the list of referenced libraries for this project
	 * @return a list of referenced libraries
	 */
	public ILibraryContainer getLibraries();

	/**
	 * Get the MOSILAB installation of that given project
	 * @return the MOSILAB installation
	 */
	public IMosilabEnvironment getMOSILABEnvironment();
	
	/**
	 * @return the eclipse project of that MOSILAB project
	 */
	public IProject getProject();
	
	/**
	 * @return the output folder for the given project
	 */
	public String getOutputFolder();
	
	/**
	 * Set the output folder for the given project
	 * @param outputFolder the folder to put all cpp files into
	 */
	public void setOutputFolder(String outputFolder);
	
	/**
	 * @return The list of the source folders of the project
	 */
	public List<IMosilabSource> getSrcFolders();

	/**
	 * convenience method for adding a source folder entry to that project
	 * @param name the name of the new source folder
	 */
	public void addSrc(String name);

	/**
	 * write properties to disk
	 */
	public void writeBackPropertiesAsync();
	
	/**
	 * Get all experiments of that project
	 * @return all experiments of that project
	 */
	public List<IExperiment> getExperiments();
	
}
