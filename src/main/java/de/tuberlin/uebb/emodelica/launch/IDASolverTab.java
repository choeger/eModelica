/**
 * 
 */
package de.tuberlin.uebb.emodelica.launch;

import java.text.DecimalFormat;
import java.util.Locale;

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
import org.eclipse.swt.widgets.Spinner;

/**
 * @author choeger
 *
 */
public class IDASolverTab extends AbstractLaunchConfigurationTab {

	//private constants no one should have to read them

	private static final String ABSOLUTE_TOLERANCE = "ABSOLUTE_TOLERANCE";
	private static final String RELATIVE_TOLERANCE = "RELATIVE_TOLERANCE";
	private static final String MAX_STEP = "MAX_STEP";
	private static final String MIN_STEP = "MIN_STEP";
	private static final String TIME_END = "TIME_END";
	private static final String TIME_START = "TIME_START";
	private static final String PRIVATE_START_UNITS_KEY = "__IDA_START_UNITS";
	private static final String PRIVATE_END_UNITS_KEY = "__IDA_END_UNITS";
	private static final String PRIVATE_START_TIME_KEY = "__IDA_START_TIME";
	private static final String PRIVATE_END_TIME_KEY = "__IDA_END_TIME";
	
	public static final String IDA_PREFIX = "DEFINES_IDA_";

	private String[] timeUnits = {"ms", "s", "m", "h", "d", "w"};
	private String[] timeFactors = {"/ 1000","","*60","*60*60","*60*60*24","*60*60*24*7"};
	private Spinner startTime;
	private Spinner endTime;
	private Combo startTimeUnit;
	private Combo endTimeUnit;
	private Spinner minSteps;
	private Spinner maxSteps;
	private Spinner absoluteTolerance;
	private Spinner relativeTolerance;
	private DecimalFormat df;
	
	public IDASolverTab() {
		df = (DecimalFormat) DecimalFormat.getInstance(Locale.US);
		df.applyPattern("0.0000");
	}
	
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
		Label relativeToleranceLabel = new Label(advancedGroup,SWT.NONE);
		relativeToleranceLabel.setText("Relative Tolerance:");
		relativeTolerance = new Spinner(advancedGroup, SWT.BORDER);
		relativeTolerance.setLayoutData(hFillData);
		relativeTolerance.setDigits(4);
		relativeTolerance.setMaximum(Integer.MAX_VALUE);
		
		Label absoluteToleranceLabel = new Label(advancedGroup,SWT.NONE);
		absoluteToleranceLabel.setText("Absolute Tolerance:");
		absoluteTolerance = new Spinner(advancedGroup, SWT.BORDER);
		absoluteTolerance.setLayoutData(hFillData);
		absoluteTolerance.setDigits(4);
		absoluteTolerance.setMaximum(Integer.MAX_VALUE);
		
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
		try {
			int startTimeUnitsIndex = configuration.getAttribute(PRIVATE_START_UNITS_KEY, 1);
			int endTimeUnitsIndex = configuration.getAttribute(PRIVATE_END_UNITS_KEY, 1);
			int startTimeValue = configuration.getAttribute(PRIVATE_START_TIME_KEY, 1);
			int endTimeValue = configuration.getAttribute(PRIVATE_END_TIME_KEY, 10);
			
			startTime.setSelection(startTimeValue);
			startTimeUnit.select(startTimeUnitsIndex);
			endTime.setSelection(endTimeValue);
			endTimeUnit.select(endTimeUnitsIndex);
			
			initSpinnerFromDoubleConfigValue(configuration, MIN_STEP, minSteps);
			initSpinnerFromDoubleConfigValue(configuration, MAX_STEP, maxSteps);
			initSpinnerFromDoubleConfigValue(configuration, RELATIVE_TOLERANCE, relativeTolerance);
			initSpinnerFromDoubleConfigValue(configuration, ABSOLUTE_TOLERANCE, absoluteTolerance);

		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param configuration
	 * @param param TODO
	 * @param spinner TODO
	 * @return
	 * @throws CoreException
	 */
	private void initSpinnerFromDoubleConfigValue(
			ILaunchConfiguration configuration, String param, Spinner spinner) throws CoreException {
		String valueString = configuration.getAttribute(IDA_PREFIX + param, "0.1");
		Integer i = Integer.valueOf(valueString.replaceAll("\\.",""),10);
		if (valueString.contains(".")) {
			spinner.setDigits(valueString.length() - (valueString.split("\\.")[0].length() + 1));
		} else spinner.setDigits(0);
		spinner.setSelection(i);
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		int startUnit = startTimeUnit.getSelectionIndex();
		configuration.setAttribute(PRIVATE_START_UNITS_KEY,startUnit);
		int endUnit = endTimeUnit.getSelectionIndex();
		configuration.setAttribute(PRIVATE_END_UNITS_KEY,endUnit);
		
		int startTimeValue = startTime.getSelection();
		configuration.setAttribute(PRIVATE_START_TIME_KEY,startTimeValue);
		int endTimeValue = endTime.getSelection();
		configuration.setAttribute(PRIVATE_END_TIME_KEY,endTimeValue);
	
		configuration.setAttribute(IDA_PREFIX + TIME_START, "" + startTimeValue + timeFactors[startUnit]);
		configuration.setAttribute(IDA_PREFIX + TIME_END, "" + endTimeValue + timeFactors[endUnit]);
		
		setDoubleConfigValueFromSpinner(configuration,MIN_STEP,this.minSteps);
		setDoubleConfigValueFromSpinner(configuration,MAX_STEP,this.maxSteps);
		setDoubleConfigValueFromSpinner(configuration,RELATIVE_TOLERANCE,this.relativeTolerance);
		setDoubleConfigValueFromSpinner(configuration,ABSOLUTE_TOLERANCE,this.absoluteTolerance);
	}

	/**
	 * @param configuration
	 */
	private void setDoubleConfigValueFromSpinner(
			ILaunchConfigurationWorkingCopy configuration, String param, Spinner spinner) {
		double value = spinner.getSelection() / Math.pow(10, spinner.getDigits());
		configuration.setAttribute(IDA_PREFIX + param,df.format(value));
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(PRIVATE_START_UNITS_KEY,1);
		configuration.setAttribute(PRIVATE_END_UNITS_KEY,1);
		
		configuration.setAttribute(PRIVATE_START_TIME_KEY,1);
		configuration.setAttribute(PRIVATE_END_TIME_KEY,10);
		configuration.setAttribute(IDA_PREFIX + TIME_START, "1.0");
		configuration.setAttribute(IDA_PREFIX + TIME_END, "10.0");
		
		configuration.setAttribute(IDA_PREFIX + MIN_STEP,"0.1");
		configuration.setAttribute(IDA_PREFIX + MAX_STEP,"10.0");
		
		configuration.setAttribute(IDA_PREFIX + RELATIVE_TOLERANCE,"0.001");
		configuration.setAttribute(IDA_PREFIX + ABSOLUTE_TOLERANCE,"1.0");
	}

}
