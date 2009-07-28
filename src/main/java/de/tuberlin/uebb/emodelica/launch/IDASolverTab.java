/**
 * 
 */
package de.tuberlin.uebb.emodelica.launch;

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
import org.eclipse.swt.widgets.Spinner;

/**
 * @author choeger
 *
 */
public class IDASolverTab extends AbstractLaunchConfigurationTab {


	public static final String IDA_PREFIX = "DEFINES_IDA_";

	private String[] timeUnits = {"ms", "s", "m", "h", "d", "w"};
	private String[] timeFactors = {"/ 1000","","*60","*60*60","*60*60*24","*60*60*24*7"};
	private Spinner startTime;
	private Spinner endTime;
	private Combo startTimeUnit;
	private Combo endTimeUnit;
	private Spinner minSteps;
	private Spinner maxSteps;
	
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridData fillData = new GridData(GridData.FILL_BOTH);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		container.setLayoutData(fillData);
		
		createTimeGroup(container);
		createStepsGroup(container);
		createAdvancedGroup(container);
		container.pack();
		setControl(container);
	}

	/**
	 * @param container
	 */
	private void createAdvancedGroup(Composite container) {
		Group advancedGroup = new Group(container,SWT.NONE);
		advancedGroup.setText("&Advanced");
		GridLayout layout = new GridLayout();
		layout.numColumns=2;
		GridData hFillData = new GridData(GridData.FILL_HORIZONTAL);
		advancedGroup.setLayoutData(hFillData);
		advancedGroup.setLayout(layout);
	}
	
	/**
	 * @param container
	 */
	private void createStepsGroup(Composite container) {
		Group stepsGroup = new Group(container,SWT.NONE);
		stepsGroup.setText("&Stepping");
		GridLayout layout = new GridLayout();
		layout.numColumns=2;
		GridData hFillData = new GridData(GridData.FILL_HORIZONTAL);
		stepsGroup.setLayoutData(hFillData);
		stepsGroup.setLayout(layout);
		Label minStepsLabel = new Label(stepsGroup,SWT.NONE);
		minStepsLabel.setText("Minimum step width:");
		minSteps = new Spinner(stepsGroup, SWT.BORDER);
		minSteps.setLayoutData(hFillData);
		minSteps.setDigits(4);
		minSteps.setMaximum(Integer.MAX_VALUE);
		
		Label maxStepsLabel = new Label(stepsGroup,SWT.NONE);
		maxStepsLabel.setText("Maximum step width:");
		maxSteps = new Spinner(stepsGroup, SWT.BORDER);
		maxSteps.setLayoutData(hFillData);
		maxSteps.setDigits(4);
		maxSteps.setMaximum(Integer.MAX_VALUE);

	}
	
	/**
	 * @param container
	 */
	private void createTimeGroup(Composite container) {
		Group timeGroup = new Group(container,SWT.NONE);
		timeGroup.setText("&Time");
		GridLayout layout = new GridLayout();
		layout.numColumns=3;
		GridData hFillData = new GridData(GridData.FILL_HORIZONTAL);
		timeGroup.setLayoutData(hFillData);
		timeGroup.setLayout(layout);
		Label startTimeLabel = new Label(timeGroup,SWT.NONE);
		startTimeLabel.setText("start:");
		startTime = new Spinner(timeGroup, SWT.BORDER);
		startTime.setLayoutData(hFillData);
		startTimeUnit = new Combo(timeGroup,SWT.READ_ONLY);
		startTimeUnit.setItems(timeUnits);
		Label endTimeLabel = new Label(timeGroup,SWT.NONE);
		endTimeLabel.setText("end:");
		endTime = new Spinner(timeGroup, SWT.BORDER);
		endTime.setLayoutData(hFillData);
		endTimeUnit = new Combo(timeGroup,SWT.READ_ONLY);
		endTimeUnit.setItems(timeUnits);
	}

	@Override
	public String getName() {
		return "IDA Solver settings";
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		// TODO Auto-generated method stub
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub
		
	}

}
