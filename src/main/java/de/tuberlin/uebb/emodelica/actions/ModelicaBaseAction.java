/**
 * 
 */
package de.tuberlin.uebb.emodelica.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchSite;



/**
 * @author choeger
 *
 */
public class ModelicaBaseAction extends Action implements IShellProvider, ISelectionChangedListener {
	private IWorkbenchSite site;
	private ISelection selection;
	
	public ModelicaBaseAction(IWorkbenchSite site) {
		this.site = site;
		site.getSelectionProvider().addSelectionChangedListener(this);
	}

	@Override
	public Shell getShell() {
		return site.getShell();
	}

	@Override
	public void selectionChanged(SelectionChangedEvent arg0) {
		this.selection = arg0.getSelection();	
	}
	
	public ISelection getSelection() {
		return selection;
	}
	
}
