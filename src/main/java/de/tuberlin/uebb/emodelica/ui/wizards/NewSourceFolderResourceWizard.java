/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.wizards;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.Wizard;

import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;

/**
 * @author choeger
 *
 */
public class NewSourceFolderResourceWizard extends Wizard {

	private NewSourceFolderWizardPage page;
	private IMosilabProject project;
	private IFolder sourceFolder;
	
	public NewSourceFolderResourceWizard(IMosilabProject project) {
		this.project = project;
		this.page = new NewSourceFolderWizardPage("New Source Folder", project);
	}
	
	@Override
	public void addPages() {
		addPage(page);
	}
	
	@Override
	public boolean performFinish() {
		sourceFolder = page.getFolder();
		return true;
	}

	/**
	 * @return the sourceFolder
	 */
	public IFolder getSourceFolder() {
		return sourceFolder;
	}

}
