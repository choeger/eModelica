/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.wizards;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author choeger
 * 
 */
public class NewEnvironmentWizardPage extends WizardPage {
	
	private Text mosilacPath;
	private Button changeButton;
	private Text mosilabRoot;
	private Text nameField;
	private Button mosilacButton;

	private class MosilacChangedListener implements SelectionListener {
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			mosilacClicked();
		}
		
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			mosilacClicked();
		}
	}
	
	private class MosilabRootChangedListener implements SelectionListener {
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			mosilabRootClicked();
		}
		
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			mosilabRootClicked();
		}
	}
	protected NewEnvironmentWizardPage(String pageName) {
		super(pageName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		this.setTitle("Select MOSILAB environment");
		GridData fillHdata = new GridData(GridData.FILL_HORIZONTAL);

		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		container.setLayout(gridLayout);

		Label label1 = new Label(container, SWT.NONE);
		label1.setText("&MOSILAB root directory:");

		mosilabRoot = new Text(container, SWT.BORDER);
		mosilabRoot.setLayoutData(fillHdata);

		mosilabRoot.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				updatePageComplete();
			}
		});

		changeButton = new Button(container, SWT.PUSH);
		changeButton.setText(JFaceResources.getString("openChange"));
		changeButton.addSelectionListener(new MosilabRootChangedListener());
		
		Label label2 = new Label(container, SWT.NONE);
		label2.setText("&Path to mosilac binary:");

		mosilacPath = new Text(container, SWT.BORDER);
		mosilacPath.setLayoutData(fillHdata);

		mosilacPath.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				updatePageComplete();
			}
		});

		mosilacButton = new Button(container, SWT.PUSH);
		mosilacButton.setText(JFaceResources.getString("openBrowse"));
		mosilacButton.addSelectionListener(new MosilacChangedListener());
		
		Label label3 = new Label(container, SWT.NONE);
		label3.setText("&Environment name:");

		nameField = new Text(container, SWT.BORDER);
		nameField.setLayoutData(fillHdata);
	
		updatePageComplete();
	}

	private void updatePageComplete() {
		setErrorMessage(null);

		String rootPath = mosilabRoot.getText();
		if (rootPath.isEmpty()) {
			mosilacButton.setEnabled(false);
			setDescription("Select the MOSILAB root directory");
			setPageComplete(false);
			return;
		} else {
			File path = new File(rootPath);
			if (path.exists()) {
				mosilacButton.setEnabled(true);
				if (mosilacPath.getText().isEmpty()) {
					File mosilacF = new File(rootPath + File.separator + "bin" + File.separator + "mosilac");
					if (mosilacF.exists()) {
						mosilacPath.setText(mosilacF.getPath());
						return; //will be invoked by setText again
					}
				}
			} else {
				setErrorMessage(rootPath + " does not exist in file system!");
				setPageComplete(false);
				return;
			}
		}
		
		String mosilac = mosilacPath.getText();
		if (mosilac.isEmpty()) {
			setDescription("Select the mosilac binary");
			setPageComplete(false);
			return;
		} else {
			File path = new File(mosilac);
			if (path.exists()) {
				try {
					Process proc = Runtime.getRuntime().exec(mosilac + " -v");
					try {
						proc.waitFor();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
					String out = reader.readLine();
					if (out.startsWith("MOSILAB"))
						nameField.setText(out);
					else {
						setMessage("Could not acquire MOSILAB name information from " + path, WizardPage.WARNING);
						nameField.setText("unknown");
					}
				} catch (IOException e) {
					setMessage("Could not acquire MOSILAB name information from " + path, WizardPage.WARNING);
					nameField.setText("unknown");
					e.printStackTrace();
				}
			} else {
				setErrorMessage(mosilac + " does not exist in file system!");
				setPageComplete(false);
			}
			//TODO: set name field
		}
		
		setPageComplete(true);
	}
	
	private void mosilacClicked() {
		FileDialog dlg = new FileDialog(this.getShell(), SWT.OPEN);
		dlg.setFilterPath(mosilabRoot.getText());
		dlg.setFilterNames(new String[] {"mosilac"});
		String fileName = dlg.open();
		if (fileName != null)
			mosilacPath.setText(fileName);
	}
	
	private void mosilabRootClicked() {
		DirectoryDialog dlg = new DirectoryDialog(this.getShell(), SWT.OPEN);
		dlg.setFilterPath(mosilabRoot.getText());
		String dirName = dlg.open();
		if (dirName != null)
			mosilabRoot.setText(dirName);		
	}

	/**
	 * @return the mosilacPath
	 */
	public Text getMosilacPath() {
		return mosilacPath;
	}

	/**
	 * @return the mosilabRoot
	 */
	public Text getMosilabRoot() {
		return mosilabRoot;
	}

	/**
	 * @return the nameField
	 */
	public Text getNameField() {
		return nameField;
	}
}
