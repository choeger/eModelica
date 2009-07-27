/**
 * 
 */
package de.tuberlin.uebb.emodelica.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.tuberlin.uebb.emodelica.ui.TextButtonGroup;

/**
 * @author choeger
 *
 */
public class MOSILABMainTab extends AbstractLaunchConfigurationTab {

	private Text projectName;
	private Combo solverSelection;
	
	//TODO: make this dynamic via an extension point
	private String[] solverTypes = {"IDA"};
	
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);
		
		GridData fillData = new GridData(GridData.FILL_BOTH);
		container.setLayoutData(fillData);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns=1;		
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
		gridLayout.numColumns=2;
		projectSelectionGroup.setLayout(gridLayout);
		projectSelectionGroup.setText("&Project");
		TextButtonGroup projectSelector = new TextButtonGroup(projectSelectionGroup, "&Browse...");
		projectSelector.getText().setLayoutData(hFillData);
		projectName = projectSelector.getText();
		//TODO: add project selection browse dialog
	}

	/**
	 * @param container
	 */
	private void createSolverSelectionGroup(Composite container) {
		Group solverSelectionGroup = new Group(container, SWT.NONE);
		GridData hFillData = new GridData(GridData.FILL_HORIZONTAL);
		solverSelectionGroup.setLayoutData(hFillData);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns=2;
		solverSelectionGroup.setLayout(gridLayout);
		solverSelectionGroup.setText("Solver");
		Label solverLabel = new Label(solverSelectionGroup,SWT.NONE);
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
			solverName = configuration.getAttribute(MOSILABLaunchDelegate.SOLVER_NAME_KEY , solverTypes[0]);
			for (int i = 0; i< solverTypes.length;i++)
				if (solverTypes[i].equals(solverName))
					solverSelection.select(i);

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(MOSILABLaunchDelegate.SOLVER_NAME_KEY, solverTypes[solverSelection.getSelectionIndex()]);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(MOSILABLaunchDelegate.SOLVER_NAME_KEY, solverTypes[0]);
	}
}
