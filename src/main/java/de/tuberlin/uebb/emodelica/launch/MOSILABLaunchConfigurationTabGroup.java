/**
 * 
 */
package de.tuberlin.uebb.emodelica.launch;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

/**
 * @author choeger
 *
 */
public class MOSILABLaunchConfigurationTabGroup extends
		AbstractLaunchConfigurationTabGroup {

	/**
	 * 
	 */
	public MOSILABLaunchConfigurationTabGroup() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTabGroup#createTabs(org.eclipse.debug.ui.ILaunchConfigurationDialog, java.lang.String)
	 */
	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {
				new MOSILABMainTab(),
				new CommonTab()
		};
		setTabs(tabs);
	}

}
