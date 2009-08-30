/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.QualifiedName;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.experiments.IExperimentContainer;
import de.tuberlin.uebb.emodelica.model.experiments.impl.ExperimentContainer;

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
	 * Get the contained experiments of that project
	 * @return all experiments of that project
	 */
	public IExperimentContainer getExperimentContainer();

	/**
	 * Set the Experiment Container
	 * @param container the new Container
	 */
	public void setExperimentContainer(IExperimentContainer container);

	/**
	 * set the MOSILAB environment that shall be used
	 * @param environment
	 */
	public void setMOSILABEnvironment(IMosilabEnvironment environment);

	/**
	 * remove that Source Folder from the given project and unmap all references to it
	 * This method is not guaranteed to run syncChildren() for performance reasons!
	 * @param src
	 */
	public void removeSource(IMosilabSource src);

	/**
	 * Add a source folder to that project and update all needed references
	 * This method is not guaranteed to run syncChildren() for performance reasons!
	 * @param src
	 */
	public void addSrc(IMosilabSource src);
}
