/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeListener;

/**
 * @author choeger
 *
 */
public interface IProjectManager extends IResourceChangeListener {
	
	public static final String MOSILAB_PROJECT_NATURE = "de.tuberlin.uebb.emodelica.nature.mosilab";
	
	/**
	 * 
	 * @param eclipseProject the eclipse IProject object to use
	 * @return the Mosilab Project for the given eclipse Project
	 */
	public IMosilabProject getMosilabProject(IProject eclipseProject);

	/**
	 * init this IProjectManager with all open projects
	 */
	public void init();
	
	/**
	 * check if a given IProject is a MOSILAB Project
	 * @param project IProject object to test
	 * @return true if project has the MOSILAB nature
	 */
	public boolean isMosilabProject(IProject project);
	
	/**
	 * return a list of all known MOSILAB projects
	 * @return all MOSILAB projects
	 */
	public List<IMosilabProject> getAllMosilabProjects();

	/**
	 * return the IMosilabSource that matches the given path
	 * @param sourceFolder
	 * @return the IMosilabSource entry or null if none exists
	 */
	public IMosilabSource getMosilabSource(String sourceFolder);
	
}
