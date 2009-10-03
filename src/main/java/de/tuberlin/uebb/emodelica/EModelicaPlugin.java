package de.tuberlin.uebb.emodelica;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabEnvironment;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;
import de.tuberlin.uebb.emodelica.model.project.IProjectManager;
import de.tuberlin.uebb.emodelica.model.project.impl.MosilabEnvironment;
import de.tuberlin.uebb.emodelica.model.project.impl.ProjectManager;
import de.tuberlin.uebb.emodelica.preferences.PreferenceConstants;
import de.tuberlin.uebb.emodelica.util.ModelicaToResourcesAdapterFactory;


/**
 * The activator class controls the plug-in life cycle
 */
public class EModelicaPlugin extends AbstractUIPlugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "de.tu-berlin.uebb.emodelica";

	private static final String HIDDEN_PROJECT_NAME = PLUGIN_ID + ".commonMosilabProject";

	// The shared instance
	private static EModelicaPlugin plugin;

	private IProjectManager projectManager;

	private List<IMosilabEnvironment> mosilabEnvironments = new ArrayList<IMosilabEnvironment>();
	
	private IProject commonMosilabResources;
	
	/**
	 * The constructor
	 */
	public EModelicaPlugin() {
		System.err.println("EModelicaPlugin()");
	}

	/**
	 * @return the commonMosilabResources
	 */
	public IProject getCommonMosilabResources() {
		return commonMosilabResources;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		System.err.println("emodelica.start()");
		
		commonMosilabResources = ResourcesPlugin.getWorkspace().getRoot().getProject(HIDDEN_PROJECT_NAME);
		
		if (!commonMosilabResources.exists()) {
			try {
				commonMosilabResources.create(null);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		commonMosilabResources.open(null);
		commonMosilabResources.setHidden(true);
		
		//first load environments, then setup projects!
		loadMosilabEnvironments();
		
		projectManager = new ProjectManager();
		projectManager.init();
		
		Platform.getAdapterManager().registerAdapters(new ModelicaToResourcesAdapterFactory(), IMosilabSource.class);
		Platform.getAdapterManager().registerAdapters(new ModelicaToResourcesAdapterFactory(), IModelicaPackage.class);

		ResourcesPlugin.getWorkspace().addResourceChangeListener(projectManager);
	}

	private void loadMosilabEnvironments() {
		mosilabEnvironments.clear();
		String allEnvironments = this.getPluginPreferences().getString(PreferenceConstants.P_MOSILAB_ENVIRONMENTS);
		System.err.println("Environments: " + allEnvironments);
		for (String envCoded : allEnvironments.split("(?<!\\\\):")) {
			if (envCoded.length() > 0)
				mosilabEnvironments.add(MosilabEnvironment.createFromString(envCoded));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		commonMosilabResources.delete(true, true, null);
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static EModelicaPlugin getDefault() {
		return plugin;
	}
	
	/**
	 * Get the project manager
	 * @return the eModelica project Manager
	 */
	public IProjectManager getProjectManager() {
		return this.projectManager ;
	}

	/**
	 * Get the list of configured MOSILAB installations
	 * @return all MOSILAB installations
	 */
	public List<IMosilabEnvironment> getMosilabEnvironments() {
		return mosilabEnvironments;
	}

	/**
	 * write back Mosilab settings to the preferences
	 */
	public void storeMosilabSettings() {
		StringBuffer buffer = new StringBuffer();
		
		for (IMosilabEnvironment env : mosilabEnvironments) {
			String code = MosilabEnvironment.encodeToString(env);
			code.replaceAll(":","\\\\:");
			buffer.append(code);
			buffer.append(":");
		}
		//remove last delimiter
		if (buffer.length() > 0)
			buffer.delete(buffer.length() - 1, buffer.length());
		getPluginPreferences().setValue(PreferenceConstants.P_MOSILAB_ENVIRONMENTS, buffer.toString());
	}

	public IMosilabEnvironment getDefaultMosilabEnvironment() {
		/* always return if only one exists */
		if (mosilabEnvironments.size() == 1)
			return mosilabEnvironments.get(0);
		
		for (IMosilabEnvironment env : mosilabEnvironments)
			if (env.isDefault())
				return env;
		
		return null;
	}
	
	/**
	 * return the current selected IResource if any
	 * @param sel
	 * @return
	 */
	public static IResource extractSelectionResource(ISelection sel) {
		if (!(sel instanceof IStructuredSelection))
			return null;
		IStructuredSelection ss = (IStructuredSelection) sel;
		Object element = ss.getFirstElement();
		if (element instanceof IResource)
			return (IResource) element;
		if (!(element instanceof IAdaptable))
			return null;
		IAdaptable adaptable = (IAdaptable) element;
		Object adapter = adaptable.getAdapter(IResource.class);
		return (IResource) adapter;
	}
	
	public static IModelicaResource extractSelectionModelicaResource(ISelection sel) {
		if (!(sel instanceof IStructuredSelection))
			return null;
		IStructuredSelection ss = (IStructuredSelection) sel;
		Object element = ss.getFirstElement();
		if (element instanceof IModelicaResource) {
			return (IModelicaResource)element;
		}
		return null;
	}

	public void setMosilabEnvironments(List<IMosilabEnvironment> environments) {
		if (this.mosilabEnvironments != null) {
			this.mosilabEnvironments.clear();
		} else {
			this.mosilabEnvironments = new ArrayList<IMosilabEnvironment>();
		}
		
		this.mosilabEnvironments.addAll(environments);
		
		for (IMosilabEnvironment env : this.mosilabEnvironments) {
			
		}
	}
}
