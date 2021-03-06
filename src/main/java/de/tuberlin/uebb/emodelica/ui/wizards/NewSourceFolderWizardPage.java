package de.tuberlin.uebb.emodelica.ui.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewSourceFolderWizardPage extends WizardPage implements ModifyListener {

	private Text pathField;
	private IFolder newFolder;
	private IContainer container;
	
	protected NewSourceFolderWizardPage(String pageName, IContainer container) {
		super(pageName);
		this.container = container;
	}

	@Override
	public void createControl(Composite arg0) {
		Composite container = new Composite(arg0, SWT.NONE);
		setControl(container);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		container.setLayout(layout);
		
		Label label = new Label(container, SWT.NONE);
		label.setText("&Folder Name");
		
		pathField = new Text(container, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		pathField.setLayoutData(data);
		
		pathField.addModifyListener(this);
	
		setTitle("Source Folder");
		setMessage("Add a new source folder");
		//TODO: setImage
	}

	@Override
	public void modifyText(ModifyEvent arg0) {
		setMessage("Add a new source folder");
		setErrorMessage(null);
		if (pathField.getText().isEmpty()) {
			setMessage("Select a new source folder.");
			this.setPageComplete(false);
			return;
		}
		
		newFolder = container.getFolder(new Path(pathField.getText()));
		
		if (newFolder.exists()) {
			setErrorMessage("The folder '" + newFolder.getFullPath() + "' already exists.");
			this.setPageComplete(false);
			return;
		}
		this.setPageComplete(true);
	}
	
	/**
	 * returns the new source folder
	 * @return a new folder for creation
	 */
	public IFolder getFolder() {
		return newFolder;
	}

}
