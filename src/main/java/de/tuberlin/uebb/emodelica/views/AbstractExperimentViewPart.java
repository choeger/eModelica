package de.tuberlin.uebb.emodelica.views;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;
import de.tuberlin.uebb.emodelica.model.experiments.IExperimentView;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IProjectManager;

public abstract class AbstractExperimentViewPart extends ViewPart implements IExperimentView {

	protected IExperiment experiment;

	public AbstractExperimentViewPart() {
		super();
	}

	@Override
	public void setExperiment(IExperiment experiment) {
		this.experiment = experiment;
		setContentDescription("Experiment from: " + experiment.getDate().toString());
		setPartName("Plot of: " + experiment.getName());
	}

	@Override
	public void saveState(IMemento memento) {
		super.saveState(memento);
		
		if(experiment != null) {
			memento.putString("PROJECT_ID", experiment.getParentProject().getProject().getName());
			memento.putString("EXPERIMENT_ID", experiment.getUniqueID());
		}
	}

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
		
		for (IExperiment experiment : project.getExperimentContainer().getExperiments()) {
			if (experiment.getUniqueID().equals(experimentID)) {
				setExperiment(experiment);
				break;
			}
		}
	}

	/**
	 * @return the experiment
	 */
	public IExperiment getExperiment() {
		return experiment;
	}

}