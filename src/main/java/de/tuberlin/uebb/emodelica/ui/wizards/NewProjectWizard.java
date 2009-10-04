/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.wizards;

import java.lang.reflect.InvocationTargetException;

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
import de.tuberlin.uebb.emodelica.model.project.IMosilabEnvironment;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IProjectManager;
import de.tuberlin.uebb.emodelica.operations.NewProjectCreationOperation;

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

		NewProjectCreationOperation op = new NewProjectCreationOperation(
				newProjectPage.getProject(), newProjectPage.useDefaultProjectLayout(), 
				newProjectPage.getMOSILABEnvironment());
		
		try {
			op.run(null);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
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
