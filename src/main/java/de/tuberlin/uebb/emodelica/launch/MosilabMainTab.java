/**
 * 
 */
package de.tuberlin.uebb.emodelica.launch;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.ui.dialogs.SelectProjectDialog;

/**
 * @author choeger
 * 
 */
public class MosilabMainTab extends AbstractLaunchConfigurationTab {

	private Text projectName;
	private Combo solverSelection;
	private IMosilabProject selectedProject;

	// TODO: make this dynamic via an extension point
	private String[] solverTypes = { "IDA" };

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);

		GridData fillData = new GridData(GridData.FILL_BOTH);
		container.setLayoutData(fillData);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		container.setLayout(gridLayout);

		createProjectSelectionGroup(container);
		createSolverSelectionGroup(container);

		container.pack();
	}

	/**
	 * @param container
	 */
	private void createProjectSelectionGroup(Composite container) {
		Group projectSelectionGroup = new Group(container, SWT.NONE);
		GridData hFillData = new GridData(GridData.FILL_HORIZONTAL);
		projectSelectionGroup.setLayoutData(hFillData);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		projectSelectionGroup.setLayout(gridLayout);
		projectSelectionGroup.setText("&Project");
		projectName = new Text(projectSelectionGroup, SWT.BORDER
				| SWT.READ_ONLY);
		projectName.setLayoutData(hFillData);
		Button projectSelector = createPushButton(projectSelectionGroup,
				"&Browse...", null);
		projectSelector.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				selectProject();
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				selectProject();
			}
		});
	}

	/*
	 * select a IMosilabProject
	 */
	protected void selectProject() {
		SelectProjectDialog dlg = new SelectProjectDialog(getShell());

		int ret = dlg.open();
		if (ret == SelectProjectDialog.OK) {
			selectedProject = dlg.getProject();
			projectName.setText(selectedProject.getProject().getName());
			updateLaunchConfigurationDialog();
		}
	}

	/**
	 * @param container
	 */
	private void createSolverSelectionGroup(Composite container) {
		Group solverSelectionGroup = new Group(container, SWT.NONE);
		GridData hFillData = new GridData(GridData.FILL_HORIZONTAL);
		solverSelectionGroup.setLayoutData(hFillData);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		solverSelectionGroup.setLayout(gridLayout);
		solverSelectionGroup.setText("Solver");
		Label solverLabel = new Label(solverSelectionGroup, SWT.NONE);
		solverLabel.setText("Select a solver:");
		solverSelection = new Combo(solverSelectionGroup, SWT.READ_ONLY);
		solverSelection.setItems(solverTypes);
		solverSelection.setLayoutData(hFillData);
	}

	@Override
	public String getName() {
		return "MOSILAB settings";
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		String solverName;
		try {
			String projectName = configuration.getAttribute(
					MosilabLaunchDelegate.PROJECT_KEY, "");
			this.projectName.setText(projectName);

			if (!projectName.isEmpty()) {
				IProject eclipseProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
				selectedProject = EModelicaPlugin.getDefault().getProjectManager().getMosilabProject(eclipseProject);
			}
			
			solverName = configuration.getAttribute(
					MosilabLaunchDelegate.SOLVER_NAME_KEY, solverTypes[0]);
			for (int i = 0; i < solverTypes.length; i++)
				if (solverTypes[i].equals(solverName))
					solverSelection.select(i);

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		// TODO: make dynamic
		configuration.setAttribute(MosilabLaunchDelegate.SOLVER_NAME_KEY,
				solverTypes[solverSelection.getSelectionIndex()]);
		configuration.setAttribute(
				MosilabLaunchDelegate.MAIN_FILE_TEMPLATE_KEY,
				"/experiments/ida_main.cpp");

		if (selectedProject != null) {
			configuration.setAttribute(MosilabLaunchDelegate.PROJECT_KEY, projectName.getText());
			configuration.setAttribute(MosilabLaunchDelegate.OUTPUT_PATH_KEY,
					selectedProject.getProject().getFolder(
							selectedProject.getOutputFolder()).getFullPath()
							.toOSString());
		}
	}

	@Override
	public boolean canSave() {
		return (selectedProject != null);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// TODO: select active project
		configuration.setAttribute(MosilabLaunchDelegate.SOLVER_NAME_KEY,
				solverTypes[0]);
	}
}