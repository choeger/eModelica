/**
 * 
 */
package de.tuberlin.uebb.emodelica.views;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;
import de.tuberlin.uebb.emodelica.model.experiments.IExperimentView;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IProjectManager;
import de.tuberlin.uebb.emodelica.ui.widgets.SimpleGraph;

/**
 * @author choeger
 *
 */
public class PlotterView extends ViewPart implements IExperimentView {
		
	ArrayList<String> vars = new ArrayList<String>();
	private IExperiment experiment;
	private SimpleGraph graph; 

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);
		graph = new SimpleGraph(container, SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);
		
		FillLayout layout = new FillLayout();
		container.setLayout(layout);
		
		//TODO: x-axis selection
		if (experiment != null)
			graph.setCurves(experiment.getCurves());

		container.pack();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setExperiment(IExperiment experiment) {
		this.experiment = experiment;
		setContentDescription("Experiment from: " + experiment.getDate().toString());
		setPartName("Plot of: " + experiment.getName());
		if (graph != null) {
			graph.setCurves(experiment.getCurves());
			graph.redraw();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.ViewPart#saveState(org.eclipse.ui.IMemento)
	 */
	@Override
	public void saveState(IMemento memento) {
		super.saveState(memento);
		
		if(experiment != null) {
			memento.putString("PROJECT_ID", experiment.getParentProject().getProject().getName());
			memento.putString("EXPERIMENT_ID", experiment.getUniqueID());
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite, org.eclipse.ui.IMemento)
	 */
	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		if (memento == null)
			return;
		
		String projectID = memento.getString("PROJECT_ID");
		if (projectID == null)
			return;
		
		String experimentID = memento.getString("EXPERIMENT_ID");
		if (experimentID == null)
			return;
		
		IProject eclipseProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectID);
		if (eclipseProject == null)
			return;
		
		IProjectManager manager = EModelicaPlugin.getDefault().getProjectManager();
		
		if (!manager.isMosilabProject(eclipseProject))
			return;
		
		IMosilabProject project = manager.getMosilabProject(eclipseProject);
		
		for (IExperiment experiment : project.getExperiments()) {
			if (experiment.getUniqueID().equals(experimentID)) {
				setExperiment(experiment);
				break;
			}
		}
	}
	
}
