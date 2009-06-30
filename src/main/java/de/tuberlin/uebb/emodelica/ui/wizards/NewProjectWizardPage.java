/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.preferences.MOSILABPreferencePage;

/**
 * @author choeger
 *
 */
public class NewProjectWizardPage extends WizardPage {

	private Text projectNameField;
	private IProject project;
	private Button useDefaultEnvironment;
	private Button useDefaultLayout;
	
	protected NewProjectWizardPage(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		this.setTitle("Create a new MOSILAB project");
		
		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);
		
		final GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		
		/* Project name selection */
		createProjectNameGroup(container);
		
		/* MOSILAB installation to use */
		createMosilabSelectionGroup(container);
		
		/* project layout settings */
		createProjectLayoutGroup(container);
		
		updatePageComplete();
	}

	private void createProjectLayoutGroup(Composite container) {
		Group projectLayout = new Group(container, SWT.NONE);
		projectLayout.setText("Project layout");
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		projectLayout.setLayoutData(gridData);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		projectLayout.setLayout(gridLayout);
		useDefaultLayout = new Button(projectLayout, SWT.RADIO);
		
		/* TODO: add confidure defaults link */
		useDefaultLayout.setText("&Create separate folders for source files and mosilac output");
		useDefaultLayout.setSelection(true);
		
		/* TODO: project root as source and output folder */
	}

	private void createMosilabSelectionGroup(Composite container) {		
		Group mosilabSelection = new Group(container, SWT.NONE);
		mosilabSelection.setText("MOSILAB installation");
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		mosilabSelection.setLayoutData(gridData);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		mosilabSelection.setLayout(gridLayout);
		
		useDefaultEnvironment = new Button(mosilabSelection, SWT.RADIO);
		
		Link confLink = new Link(mosilabSelection, SWT.NONE);
		confLink.setText("<A>Configure MOSILAB</A>");
		confLink.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				openPreferencePage();
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				openPreferencePage();
			}
		});
		
		updateDefaultEnvironment();
		useDefaultEnvironment.setSelection(true);
		
		/* TODO: add project specific selection */
	}

	/**
	 * @param environmentName
	 */
	private void updateDefaultEnvironment() {
		String environmentName = "none";
		if (EModelicaPlugin.getDefault().getDefaultMosilabEnvironment() != null)
			environmentName = EModelicaPlugin.getDefault().getDefaultMosilabEnvironment().getName();
		
		useDefaultEnvironment.setText("use default installation (Currently '" + 
				environmentName + "')");
	}

	private void openPreferencePage() {
		IPreferencePage page = new MOSILABPreferencePage();
		PreferenceManager mgr = new PreferenceManager();
		IPreferenceNode node = new PreferenceNode("1", page);
		mgr.addToRoot(node);
		PreferenceDialog dialog = new PreferenceDialog(this.getShell(), mgr);
		dialog.create();
		dialog.setMessage(page.getTitle());
		dialog.open();
		updateDefaultEnvironment();
		updatePageComplete();
	}
	
	/**
	 * @param container
	 */
	private void createProjectNameGroup(Composite container) {
		final Label label1 = new Label(container, SWT.NONE);
		final GridData gridDataLabel1 = new GridData();
		gridDataLabel1.horizontalSpan = 1;
		label1.setLayoutData(gridDataLabel1);
		label1.setText("&Project name:");

		projectNameField = new Text(container, SWT.BORDER);
		projectNameField.addModifyListener(
				new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent arg0) {
						updatePageComplete();
					}});
		final GridData gridDataProjectNameField = new GridData(GridData.FILL_HORIZONTAL);
		projectNameField.setLayoutData(gridDataProjectNameField);
	}

	protected void updatePageComplete() {
		boolean valid = false;
		setErrorMessage(null);
		
		if (projectNameField.getText().isEmpty()) {
			setDescription("Select a project name");
		} else if (EModelicaPlugin.getDefault().getDefaultMosilabEnvironment() == null) {
			setErrorMessage("You have to install a MOSILAB environment first!");
		} else {
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectNameField.getText());
			if (project.exists()) {
				setErrorMessage("A project with this name already exists.");
				valid = false;
			} else {
				setDescription("Create a MOSILAB project in the workspace");
				valid = true;
			}
		} 
		setPageComplete(valid);
	}

	public IProject getProject() {
		return project;
	}
	
	public boolean useDefaultProjectLayout() {
		return useDefaultLayout.getSelection();
	}
	
	public boolean useDefaultEnvironment() {
		return useDefaultEnvironment.getSelection();
	}

}
