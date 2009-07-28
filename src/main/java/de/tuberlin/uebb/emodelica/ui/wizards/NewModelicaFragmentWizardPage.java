/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.wizards;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;
import de.tuberlin.uebb.emodelica.ui.IWidgetDelegate;
import de.tuberlin.uebb.emodelica.ui.ModelicaFragmentCreationPage;

/**
 * @author choeger
 *
 */
public class NewModelicaFragmentWizardPage extends WizardPage implements ModifyListener, IWidgetDelegate  {

	private ModelicaFragmentCreationPage widget;
	private IMosilabSource defaultSrc;
	private IModelicaPackage defaultPkg;
	private String defaultKind;
	
	protected NewModelicaFragmentWizardPage(String pageName) {
		super(pageName);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite arg0) {
		initializeDialogUnits(arg0);
		setTitle("Modelica element");
		setDescription("Create a new Modelica element");
		
		widget = new ModelicaFragmentCreationPage(arg0, SWT.NONE, this);
		widget.getSrcGrp().getText().addModifyListener(this);
		widget.getPkgGrp().getText().addModifyListener(this);
		widget.getNameText().addModifyListener(this);
		
		if (defaultPkg != null)
			widget.getPkgGrp().getText().setText(defaultPkg.getFullName());
		if (defaultSrc != null)
			widget.getSrcGrp().getText().setText(defaultSrc.getBasePath().getFullPath().toOSString());
		if (defaultKind != null)
			widget.getKindCombo().setText(defaultKind);
		
		setButtonLayoutData(widget.getPkgGrp().getButton());
		setButtonLayoutData(widget.getSrcGrp().getButton());
		setButtonLayoutData(widget.getTypeGrp().getButton1());
		setButtonLayoutData(widget.getTypeGrp().getButton2());
		
		
		setControl(widget);
		updatePageComplete();
	}
	
	private void updatePageComplete() {
		boolean valid = true;
		enableAll();
		String srcFolder = widget.getSrcGrp().getText().getText();
		String typeName = widget.getNameText().getText();
		setErrorMessage(null);
		
		if (srcFolder.isEmpty()) {
			valid = false;
			setErrorMessage("Source folder name is empty");
			enableOnlySource();
		} else if (!ResourcesPlugin.getWorkspace().getRoot().
				getFolder(new Path(srcFolder)).exists()) {
			valid = false;
			setErrorMessage("Folder " + srcFolder + " does not exist!");
			enableOnlySource();
		} else if (typeName.isEmpty()) {
			valid = false;
			setErrorMessage("Type name is empty");
		} 		
		setPageComplete(valid);
	}
	
	private void enableAll() {
		widget.getSrcGrp().getButton().setEnabled(true);		
		widget.getPkgGrp().getButton().setEnabled(true);
		//temporary to avoid non functioning widgets
		widget.getTypeGrp().getText().setEnabled(false);
		widget.getTypeGrp().getButton1().setEnabled(false);
		widget.getTypeGrp().getButton2().setEnabled(false);
	}

	private void enableOnlySource() {
		widget.getSrcGrp().getButton().setEnabled(true);		
		widget.getPkgGrp().getButton().setEnabled(false);
		widget.getTypeGrp().getText().setEnabled(false);
		widget.getTypeGrp().getButton1().setEnabled(false);
		widget.getTypeGrp().getButton2().setEnabled(false);
	}


	@Override
	public void modifyText(ModifyEvent arg0) {
		updatePageComplete();
	}

	public void setSourceDir(IMosilabSource src) {
		this.defaultSrc = src;
	}
	
	public void setPkg(IModelicaPackage pkg) {
		this.defaultPkg = pkg;
	}
	
	public void setKind(String kind) {
		this.defaultKind = kind;
	}

	public String getSourceFolder() {
		return widget.getSrcGrp().getText().getText();
	}

	public String getPackageName() {
		return widget.getPkgGrp().getText().getText();
	}
	
	public String getTypeName() {
		return widget.getNameText().getText();
	}	
		
	public String getFragmentKind() {
		return widget.getKindCombo().getText();
	}

	@Override
	public Button createButton(Composite parent, String label) {
		Button button = new Button(parent, SWT.NONE);
		button.setText(label);
		this.setButtonLayoutData(button);
		return button;
	}
}
