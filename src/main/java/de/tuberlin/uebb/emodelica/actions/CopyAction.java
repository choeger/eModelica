/**
 * 
 */
package de.tuberlin.uebb.emodelica.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.DeleteResourceAction;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;

/**
 * @author choeger
 * 
 */
public class CopyAction extends ModelicaBaseAction {
	

	public CopyAction(IWorkbenchSite site) {
		super(site);
		setText("&Copy");
		setDescription("Copy");

		ISharedImages workbenchImages = EModelicaPlugin.getDefault()
				.getWorkbench().getSharedImages();
		setDisabledImageDescriptor(workbenchImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
		setImageDescriptor(workbenchImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setHoverImageDescriptor(workbenchImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
	}

	private IAction createWorkbenchAction(IStructuredSelection selection) {
		DeleteResourceAction action = new DeleteResourceAction(this);
		action.selectionChanged(selection);
		return action;
	}

	public void run(IStructuredSelection selection) {
		System.err.println("copy " + selection.getFirstElement());
	}
	
	
}
