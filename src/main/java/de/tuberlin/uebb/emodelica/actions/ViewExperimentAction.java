/**
 * 
 */
package de.tuberlin.uebb.emodelica.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PartInitException;

import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;
import de.tuberlin.uebb.emodelica.model.experiments.IExperimentView;


/**
 * @author choeger
 *
 */
public class ViewExperimentAction extends ModelicaBaseAction {

	private String viewID;
	private IExperiment experiment;
	
	public ViewExperimentAction(String viewerName, 
			String viewerID, ImageDescriptor icon, IWorkbenchSite site) {
		super(site);
		setText(viewerName);
		this.viewID= viewerID;
		
		setImageDescriptor(icon);
		
		setDescription("View experiment in " + viewerName);
	}
	
	@Override
	public void run() {
		try {
			IViewPart viewPart = getSite().getWorkbenchWindow().getActivePage().showView(viewID);
			if (viewPart instanceof IExperimentView) {
				((IExperimentView)viewPart).setExperiment(experiment);
			}
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/**
	 * @return the experiment
	 */
	public IExperiment getExperiment() {
		return experiment;
	}



	/**
	 * @param experiment the experiment to set
	 */
	public void setExperiment(IExperiment experiment) {
		this.experiment = experiment;
	}

}
