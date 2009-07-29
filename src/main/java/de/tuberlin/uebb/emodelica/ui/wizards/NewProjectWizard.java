/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IProjectManager;

/**
 * @author choeger
 * 
 */
public class NewProjectWizard extends Wizard implements INewWizard, IExecutableExtension {

	NewProjectWizardPage newProjectPage = new NewProjectWizardPage(
			"new project");

	private IConfigurationElement configElement;

	@Override
	public void addPages() {
		addPage(newProjectPage);
	}

	/**
	 * 
	 */
	public NewProjectWizard() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		IProject project = newProjectPage.getProject();

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
			return false;
		}

		IMosilabProject newMosilabProject = EModelicaPlugin.getDefault().getProjectManager().getMosilabProject(project);
		
		if (newProjectPage.useDefaultProjectLayout()) {
			newMosilabProject.setOutputFolder("c++/");
			newMosilabProject.addSrc("src");
		}
		
		BasicNewProjectResourceWizard.updatePerspective(configElement);
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench arg0, IStructuredSelection arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		this.configElement = config;		
	}

}
