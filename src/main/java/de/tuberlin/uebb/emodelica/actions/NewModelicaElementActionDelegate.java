/**
 * 
 */
package de.tuberlin.uebb.emodelica.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;

import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.emodelica.ui.wizards.NewModelicaFragmentWizard;

/**
 * @author choeger
 *
 */
public class NewModelicaElementActionDelegate implements
		IWorkbenchWindowPulldownDelegate {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	@Override
	public void init(IWorkbenchWindow arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction arg0) {
		NewModelicaFragmentWizard newFragmentWizard = new NewModelicaFragmentWizard();
		WizardDialog dialog = new WizardDialog(Display.getCurrent().getActiveShell(), newFragmentWizard);
		dialog.open();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction arg0, ISelection arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public Menu getMenu(Control parent) {
		Menu menu = new Menu(parent);
		
		MenuItem newClassItem = new MenuItem(menu, SWT.NONE);
		newClassItem.setText("Class");
		newClassItem.setImage(Images.CLASS_IMAGE_DESCRIPTOR.createImage());
		
		MenuItem newModelItem = new MenuItem(menu, SWT.NONE);
		newModelItem.setText("Model");
		newModelItem.setImage(Images.MODEL_IMAGE_DESCRIPTOR.createImage());
		
		MenuItem newConnectorItem = new MenuItem(menu, SWT.NONE);
		newConnectorItem.setText("Connector");
		newConnectorItem.setImage(Images.PLUGIN_IMAGE_DESCRIPTOR.createImage());
		
		//TODO: add more elements
		
		return menu;
	}

}
