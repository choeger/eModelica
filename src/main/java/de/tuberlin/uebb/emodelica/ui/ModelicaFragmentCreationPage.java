/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;
import de.tuberlin.uebb.emodelica.ui.dialogs.SelectPackageDialog;
import de.tuberlin.uebb.emodelica.ui.dialogs.SelectSourceFolderDialog;

/**
 * @author choeger
 *
 */
public class ModelicaFragmentCreationPage extends Composite {

	private LabelTextButtonGroup srcGrp;
	private LabelTextButtonGroup pkgGrp;
	private ButtonTextButtonGroup typeGrp;
	private Text nameText;
	private Combo kindCombo;
	private IWidgetDelegate delegate;

	public ModelicaFragmentCreationPage(Composite parent, int style, IWidgetDelegate delegate) {
		super(parent, style);
		this.delegate = delegate;
		initialize();
	}

	private void initialize() {
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		this.setLayout(layout);
		GridData hFillData = new GridData(GridData.FILL_HORIZONTAL);
		
		srcGrp = new LabelTextButtonGroup(this, "&Source Folder:", "Browse...", delegate);
		srcGrp.getText().setLayoutData(hFillData);
		pkgGrp = new LabelTextButtonGroup(this, "&Package:", "Browse...", delegate);
		pkgGrp.getText().setLayoutData(hFillData);
		typeGrp = new ButtonTextButtonGroup(this, "&Enclosing type:", "Browse...");
		typeGrp.getText().setLayoutData(hFillData);
		
		srcGrp.getButton().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				browseSource();
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				browseSource();				
			}});
		
		pkgGrp.getButton().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				browsePackage();
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				browsePackage();				
			}});
		
		
		Label shadow_sep_h = new Label(this, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
		//fluid builders are cool!
		shadow_sep_h.setLayoutData(GridDataFactory.createFrom(hFillData).span(3, 1).create());
		
		Label nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("&Name:");
		
		nameText = new Text(this, SWT.BORDER);
		nameText.setLayoutData(hFillData);

		Composite empty = new Composite(this, SWT.NONE);
		
		Label kindLabel = new Label(this, SWT.NONE);
		kindLabel.setText("&Kind:");
		kindCombo = new Combo(this, SWT.BORDER);
		kindCombo.setLayoutData(hFillData);

		kindCombo.add("package");
		kindCombo.add("class");
		kindCombo.add("function");
		kindCombo.add("model");
		kindCombo.add("connector");
		kindCombo.add("record");
		
		this.pack();
	}

	protected void browsePackage() {
		SelectPackageDialog dialog = new SelectPackageDialog(this.getShell(), srcGrp.getText().getText());
		if (dialog.open() == Dialog.OK)
			pkgGrp.getText().setText(((IModelicaPackage)dialog.getFirstResult()).getFullName());		

	}

	protected void browseSource() {
		SelectSourceFolderDialog dialog = new SelectSourceFolderDialog(this.getShell());
		if (dialog.open() == Dialog.OK)
			srcGrp.getText().setText(((IMosilabSource)dialog.getFirstResult()).getBasePath().getFullPath().toOSString());		
	}

	/**
	 * @return the srcGrp
	 */
	public LabelTextButtonGroup getSrcGrp() {
		return srcGrp;
	}

	/**
	 * @return the pkgGrp
	 */
	public LabelTextButtonGroup getPkgGrp() {
		return pkgGrp;
	}

	/**
	 * @return the typeGrp
	 */
	public ButtonTextButtonGroup getTypeGrp() {
		return typeGrp;
	}

	/**
	 * @return the nameText
	 */
	public Text getNameText() {
		return nameText;
	}

	/**
	 * @return the kindCombo
	 */
	public Combo getKindCombo() {
		return kindCombo;
	}

}  //  @jve:decl-index=0:visual-constraint="474,-9"
