/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.wizard.Wizard;

/**
 * @author choeger
 *
 */
public class NewSourceFolderResourceWizard extends Wizard {

	private NewSourceFolderWizardPage page;
	private IFolder sourceFolder;
	
	public NewSourceFolderResourceWizard(IContainer container) {
		this.page = new NewSourceFolderWizardPage("New Source Folder", container);
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
