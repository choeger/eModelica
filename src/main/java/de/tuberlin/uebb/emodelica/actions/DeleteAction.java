/**
 * 
 */
package de.tuberlin.uebb.emodelica.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.DeleteResourceAction;
import org.eclipse.ui.ide.undo.DeleteResourcesOperation;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;
import de.tuberlin.uebb.emodelica.model.experiments.impl.TextFileExperiment;
import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;

/**
 * @author choeger
 * 
 */
public class DeleteAction extends ModelicaBaseAction {
	
	public DeleteAction(IWorkbenchSite site) {
		super(site);
		setText("&Delete");
		setDescription("Delete");

		ISharedImages workbenchImages = EModelicaPlugin.getDefault()
				.getWorkbench().getSharedImages();
		setDisabledImageDescriptor(workbenchImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
		setImageDescriptor(workbenchImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		setHoverImageDescriptor(workbenchImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
	}

	private IAction createWorkbenchAction(IStructuredSelection selection) {
		DeleteResourceAction action = new DeleteResourceAction(this);
		action.selectionChanged(selection);
		return action;
	}

	@Override
	public void run() {
		ISelection selection = getSelection();
		if (selection instanceof IStructuredSelection) {
			Object toDelete = ((IStructuredSelection)getSelection()).getFirstElement();
			
			//TODO: use adapters when they exist!
			DeleteResourcesOperation delOp = null;
			if (toDelete instanceof TextFileExperiment) {		
				TextFileExperiment experiment = (TextFileExperiment)toDelete;
				delOp = new DeleteResourcesOperation(new IResource[] {experiment.getFile()},"deleting",true);
			}
			
			if (toDelete instanceof IModelicaResource) {
				
				if (toDelete instanceof IMosilabSource) {
					IMosilabSource mosilabSource = (IMosilabSource)toDelete;
					
					IFolder folder = mosilabSource.getBasePath();
					delOp = new DeleteResourcesOperation(new IResource[] {folder},"deleting",true);
				}
				
				if (toDelete instanceof IModelicaPackage) {
					IModelicaPackage pkg = (IModelicaPackage)toDelete;
					
					//TODO: only delete stuff, that is shown as package resource!
					IFolder folder = (IFolder) pkg.getContainer();
					delOp = new DeleteResourcesOperation(new IResource[] {folder},"deleting",true);
				}
			}
			
			if (delOp != null)
				try {
					delOp.execute(null, null);
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
		}
	}
	
	
}
