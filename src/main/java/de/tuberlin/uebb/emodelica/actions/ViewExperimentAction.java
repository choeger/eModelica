/**
 * 
 */
package de.tuberlin.uebb.emodelica.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PartInitException;

import de.tuberlin.uebb.emodelica.model.experiments.IExperimentView;


/**
 * @author choeger
 *
 */
public class ViewExperimentAction extends ModelicaBaseAction {

	private String viewName;
	private String iconPath;
	private String viewID;
	
	public ViewExperimentAction(String viewerName, 
			String viewerID, String icon, IWorkbenchSite site) {
		super(site);
		setText(viewerName);
		this.viewName=viewerName;
		this.iconPath = icon;
		this.viewID= viewerID;
		
		setImageDescriptor(ImageDescriptor.createFromFile(this.getClass(), iconPath));
		
		setDescription("View experiment in " + viewerName);
	}
	
	@Override
	public void run() {
		try {
			getSite().getWorkbenchWindow().getActivePage().showView(viewID);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
