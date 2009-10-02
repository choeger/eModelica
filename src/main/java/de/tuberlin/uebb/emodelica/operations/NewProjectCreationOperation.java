/**
 * 
 */
package de.tuberlin.uebb.emodelica.operations;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.IMosilabEnvironment;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IProjectManager;

/**
 * @author choeger
 *
 */
public class NewProjectCreationOperation extends WorkspaceModifyOperation {

	private IProject project;
	private boolean useDefaultProjectLayout;
	private IMosilabEnvironment environment;

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.WorkspaceModifyOperation#execute(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void execute(IProgressMonitor monitor) throws CoreException,
			InvocationTargetException, InterruptedException {
		// TODO Auto-generated method stub
		try {
			project.create(null);
			project.open(null);
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
			newNatures[natures.length] = IProjectManager.MOSILAB_PROJECT_NATURE;
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}

		IMosilabProject newMosilabProject = EModelicaPlugin.getDefault().getProjectManager().getMosilabProject(project);
		
		if (useDefaultProjectLayout) {
			newMosilabProject.setOutputFolder("c++/");
			newMosilabProject.addSrc("src");
		}
		
		newMosilabProject.setMOSILABEnvironment(environment);
	
		newMosilabProject.writeBackProperties();
	}

	public NewProjectCreationOperation(IProject project,
			boolean useDefaultProjectLayout, IMosilabEnvironment environment) {
		super();
		this.project = project;
		this.useDefaultProjectLayout = useDefaultProjectLayout;
		this.environment = environment;
	}

}
