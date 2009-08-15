/**
 * 
 */
package de.tuberlin.uebb.emodelica.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;

import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;

/**
 * @author choeger
 * 
 */
public class CCPActionGroup extends ActionGroup {
	private IWorkbenchSite site;
	private Clipboard clipboard;

	/* actions */
	private ModelicaBaseAction deleteAction;
	private ModelicaBaseAction cutAction;
	private ModelicaBaseAction copyAction;
	private ModelicaBaseAction pasteAction;

	private ModelicaBaseAction[] actions;
	
	public CCPActionGroup(IViewPart part) {
		this(part.getSite());
	}

	public CCPActionGroup(Page page) {
		this((IWorkbenchPartSite) page.getSite());
	}

	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		for (int i = 0; i < actions.length; i++) {
			ModelicaBaseAction action = actions[i];
			menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, action);
		}
	}

	public CCPActionGroup(IWorkbenchPartSite site) {
		this.site = site;
		clipboard = new Clipboard(site.getShell().getDisplay());

		deleteAction = new DeleteAction(site);
		deleteAction.setActionDefinitionId(IWorkbenchActionDefinitionIds.DELETE);
		
		//TODO: add clipboard parameter
		
		copyAction = new CopyAction(site);
		copyAction.setActionDefinitionId(IWorkbenchActionDefinitionIds.COPY);

		pasteAction = new PasteAction(site);
		pasteAction.setActionDefinitionId(IWorkbenchActionDefinitionIds.PASTE);
		
		cutAction = new CutAction(site);
		cutAction.setActionDefinitionId(IWorkbenchActionDefinitionIds.CUT);
		
		//TODO: register for selection change events
		
		actions = new ModelicaBaseAction[] { cutAction, copyAction, pasteAction, deleteAction };						

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.ActionGroup#setContext(org.eclipse.ui.actions.ActionContext)
	 */
	@Override
	public void setContext(ActionContext context) {
		super.setContext(context);
		System.err.println("context: " + context);
		if (context == null || context.getSelection() == null)
			return;
		
		if (context.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection)context.getSelection();
			
			if (selection.getFirstElement() instanceof IExperiment) {
				deleteAction.setEnabled(true);
				copyAction.setEnabled(false);
				pasteAction.setEnabled(false);
				cutAction.setEnabled(false);
			}
		}
	}

}
