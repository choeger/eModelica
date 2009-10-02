/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.wizards;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;
import de.tuberlin.uebb.emodelica.ui.dialogs.SelectSourceFolderDialog;

/**
 * @author choeger
 *
 */
public class NewPackageWizardPage extends WizardPage {

	protected NewPackageWizardPage(String pageName) {
		super(pageName);
	}

	private Text packageNameField;
	private Text sourceDirField;
	private IMosilabSource srcPath = null;
	private IModelicaPackage parentPkg;
	
	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		setTitle("new Modelica package");
		
		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);
		
		final GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		container.setLayout(layout);
		
		createTopLabel(container);
		
		createSourceFolderEntry(container);
		
		createPackageNameField(container);
	}

	/**
	 * @param container
	 */
	private void createPackageNameField(Composite container) {
		Label label1 = new Label(container, SWT.NONE);
		label1.setText("Package name:");
		
		packageNameField = new Text(container, SWT.BORDER);
		if (parentPkg != null)
			packageNameField.setText(parentPkg.getFullName());
		
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		packageNameField.setLayoutData(data);
	}

	/**
	 * @param container
	 */
	private void createSourceFolderEntry(Composite container) {
		Label label1 = new Label(container, SWT.NONE);
		label1.setText("Source folder:");
		
		sourceDirField = new Text(container, SWT.BORDER);

		Button browseButton = new Button(container, SWT.PUSH);
		browseButton.setText(JFaceResources.getString("openBrowse"));
		setButtonLayoutData(browseButton);
		
		browseButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				browseButtonClicked();				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				browseButtonClicked();				
			}});
		
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		sourceDirField.setLayoutData(data);
		if (srcPath != null)
			sourceDirField.setText(srcPath.getResource().getFullPath().toOSString());
	}

	private void browseButtonClicked() {
		SelectSourceFolderDialog dialog = new SelectSourceFolderDialog(this.getShell());
		if (dialog.open() == Dialog.OK);
			sourceDirField.setText(((IMosilabSource)dialog.getFirstResult()).getBasePath().getFullPath().toOSString());
	}

	/**
	 * @param container
	 */
	private void createTopLabel(Composite container) {
		Label label1 = new Label(container, SWT.NONE);
		label1.setText("Creates a folder structure and a top level package.mo");
		
		GridData data = new GridData();
		data.horizontalSpan = 3;
		label1.setLayoutData(data);
	}
	
	/**
	 * 
	 * @return the name for the new package
	 */
	public String getPackageName() {
		return packageNameField.getText();
	}
	
	/**
	 * 
	 * @return the source folder for the new package
	 */
	public String getSourceFolder() {
		return sourceDirField.getText();
	}

	/**
	 * @return the packageNameField
	 */
	public Text getPackageNameField() {
		return packageNameField;
	}

	/**
	 * @param packageNameField the packageNameField to set
	 */
	public void setPackageNameField(Text packageNameField) {
		this.packageNameField = packageNameField;
	}

	/**
	 * @return the parentPkg
	 */
	public IModelicaPackage getParentPkg() {
		return parentPkg;
	}

	/**
	 * @param parentPkg the parentPkg to set
	 */
	public void setParentPkg(IModelicaPackage parentPkg) {
		this.parentPkg = parentPkg;
	}

	/**
	 * @return the srcPath
	 */
	public IMosilabSource getSrcPath() {
		return srcPath;
	}

	/**
	 * @param srcPath the srcPath to set
	 */
	public void setSrcPath(IMosilabSource srcPath) {
		this.srcPath = srcPath;
	}
}
