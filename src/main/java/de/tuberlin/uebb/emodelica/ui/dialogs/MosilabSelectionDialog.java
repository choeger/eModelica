/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.IMosilabEnvironment;
import de.tuberlin.uebb.emodelica.ui.widgets.MosilabSelection;

/**
 * @author choeger
 *
 */
public class MosilabSelectionDialog extends Dialog {

	private static final EModelicaPlugin plugin = EModelicaPlugin.getDefault();
	private MosilabSelection selection;
	private IMosilabEnvironment currentEnvironment;
	
	public MosilabSelectionDialog(Shell parentShell, IMosilabEnvironment current) {
		super(parentShell);
		this.currentEnvironment = current;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		selection = new MosilabSelection(parent, SWT.NONE);
		if (currentEnvironment.isDefault()) {
			selection.getUseDefaultEnvironment().setSelection(true);
			selection.getUseAlternateEnvironment().setSelection(false);
		} else {
			int index = plugin.getMosilabEnvironments().indexOf(currentEnvironment);
			selection.getUseDefaultEnvironment().setSelection(false);
			selection.getUseAlternateEnvironment().setSelection(true);
			selection.getAlternateEnvironmentSelection().select(index);
		}
		
		selection.addSelectionChangedListener(new ISelectionChangedListener(){

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (selection.getUseDefaultEnvironment().getSelection()) {
					currentEnvironment = plugin.getDefaultMosilabEnvironment();
				} else {
					currentEnvironment = plugin.getMosilabEnvironments().get(
							selection.getAlternateEnvironmentSelection().getSelectionIndex());
				}
					
					
			}});
		
		return super.createDialogArea(parent);
	}
	
	
	public IMosilabEnvironment getSelectedEnvironment() {
		return currentEnvironment;
	}
	
	

}
