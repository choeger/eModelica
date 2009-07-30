/**
 * 
 */
package de.tuberlin.uebb.emodelica.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

/**
 * @author choeger
 *
 */
public class ObservableTab extends AbstractLaunchConfigurationTab implements ModifyListener {

	private Text observableText;
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
			Composite container = new Composite(parent, SWT.NONE);
			setControl(container);

			GridData fillData = new GridData(GridData.FILL_BOTH);
			container.setLayoutData(fillData);
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 1;
			container.setLayout(gridLayout);
			
			createObservableGroup(container);

			container.pack();
		}

		private void createObservableGroup(Composite container) {
			Group observableGroup = new Group(container, SWT.NONE);
			GridData hFillData = new GridData(GridData.FILL_HORIZONTAL);
			observableGroup.setLayoutData(hFillData);
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 1;
			observableGroup.setLayout(gridLayout);
			observableGroup.setText("&Observables");
			
			observableText = new Text(observableGroup,SWT.BORDER);
			observableText.setLayoutData(hFillData);
			observableText.addModifyListener(this);
		}	

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	@Override
	public String getName() {
		return "Observables";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		String observableString;
		try {
			observableString = configuration.getAttribute(MosilabLaunchDelegate.OBSERVABLES_KEY, "time ");
			observableText.setText(observableString);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(MosilabLaunchDelegate.OBSERVABLES_KEY, observableText.getText());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(MosilabLaunchDelegate.OBSERVABLES_KEY, "time ");
	}

	@Override
	public void modifyText(ModifyEvent e) {
		updateLaunchConfigurationDialog();
	}

}
