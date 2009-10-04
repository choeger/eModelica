/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.IMosilabEnvironment;
import de.tuberlin.uebb.emodelica.ui.widgets.MosilabSelection;

/**
 * @author choeger
 *
 */
public class NewProjectWizardPage extends WizardPage {

	private Text projectNameField;
	private IProject project;
	private Button useDefaultEnvironment;
	private Button useDefaultLayout;
	private MosilabSelection mosilabSelection;
	private IMosilabEnvironment environment;
	
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
		initializeDialogUnits(container);
		
		Group projectLayout = new Group(container, SWT.NONE);
		projectLayout.setText("Project layout");
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		projectLayout.setLayoutData(gridData);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		projectLayout.setLayout(gridLayout);
		useDefaultLayout = new Button(projectLayout, SWT.RADIO);
		
		/* TODO: add configure defaults link */
		useDefaultLayout.setText("&Create separate folders for source files and mosilac output");
		useDefaultLayout.setSelection(true);
		
		/* TODO: project root as source and output folder */
	}

	private void createMosilabSelectionGroup(Composite container) {		
		mosilabSelection = new MosilabSelection(container, SWT.NONE);

		mosilabSelection.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				updatePageComplete();				
			}
			
		});
		
		useDefaultEnvironment = mosilabSelection.getUseDefaultEnvironment();
		setButtonLayoutData(mosilabSelection.getOpenInstalledEnvironments());
		mosilabSelection.getOpenInstalledEnvironments().pack();
		useDefaultEnvironment.setSelection(true);
		mosilabSelection.getAlternateEnvironmentSelection().setEnabled(false);
		
		/* TODO: add project specific selection */
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
		
		if (mosilabSelection.getUseDefaultEnvironment().getSelection())
			environment = EModelicaPlugin.getDefault().getDefaultMosilabEnvironment();
		else if (mosilabSelection.getUseAlternateEnvironment().getSelection())
			environment = EModelicaPlugin.getDefault().getMosilabEnvironments().get(
					mosilabSelection.getAlternateEnvironmentSelection().getSelectionIndex());
		else environment = null;
		
		if (environment == null)
			setMessage("Without a MOSILAB environment the project cannot be build.", NewProjectWizardPage.WARNING);
		
		
		if (projectNameField.getText().isEmpty()) {
			setDescription("Select a project name");
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

	public IMosilabEnvironment getMOSILABEnvironment() {
		return environment;
	}

}
