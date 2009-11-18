/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.project.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;
import de.tuberlin.uebb.emodelica.model.project.IProjectManager;

/**
 * @author choeger
 * 
 */
public class ProjectManager implements IProjectManager, IResourceChangeListener {

	class DeltaVisitor implements IResourceDeltaVisitor {

		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {

			if (delta.getResource().getType() == IResource.PROJECT) {
				IProject project = (IProject) delta.getResource();
				String path = project.getFullPath().toOSString();

				if (delta.getKind() == IResourceDelta.OPEN) {
					if (project.isOpen()) {
						/* new opened MOSILAB project */
						if (project.getNature(MOSILAB_PROJECT_NATURE) != null) {
							projects.put(path, new MosilabProject(project));
						}
					} else {
						/* closed MOSILAB project */
						projects.remove(path);
					}
					/* new or closed - our job is done */
					return false;
				}
			}

			IResource resource = delta.getResource();
			while (resource != null) {
				IModelicaResource modelicaResource = (IModelicaResource) delta
						.getResource().getAdapter(IModelicaResource.class);
				if (modelicaResource != null)
					modelicaResource.markAsDirty();

				resource = resource.getParent();
			}
			return true;
		}
	}

	private HashMap<String, IMosilabProject> projects = new HashMap<String, IMosilabProject>();
	private DeltaVisitor visitor = new DeltaVisitor();

	@Override
	public IMosilabProject getMosilabProject(IProject eclipseProject) {
		String path = projectKey(eclipseProject);
		if (projects.containsKey(path))
			return projects.get(path);
		else {
			MosilabProject mProject = new MosilabProject(eclipseProject);
			projects.put(path, mProject);
			return mProject;
		}
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		try {
			if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
				IProject project = (IProject) event.getResource();
				if (project.getNature(MOSILAB_PROJECT_NATURE) != null) {
					IMosilabProject mpj = getMosilabProject(project);
					mpj.writeBackPropertiesGuarded();
				}
			} else if (event.getDelta() != null) {
				event.getDelta().accept(visitor);
				
				WorkspaceJob refreshJob = new WorkspaceJob("Refresh MOSILAB workspace") {

					@Override
					public IStatus runInWorkspace(IProgressMonitor monitor)
							throws CoreException {
						refreshProjects();
						return new Status(IStatus.OK, EModelicaPlugin.PLUGIN_ID, "");
					}				
				};
				
				refreshJob.schedule();
			}
				
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/**
		 * 
		 */
	private final void refreshProjects() {
		List<IMosilabProject> toDelete = new ArrayList<IMosilabProject>();

		for (IMosilabProject project : projects.values()) {
			if (project.getProject().exists() && project.getProject().isOpen()) {
				project.refresh();
				project.syncChildren();
			} else {
				toDelete.add(project);
			}
		}

		for (IMosilabProject project : toDelete)
			projects.remove(projectKey(project.getProject()));
	};

	@Override
	public void init() {
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot()
				.getProjects()) {
			try {
				if (project.isOpen()
						&& project.getNature(MOSILAB_PROJECT_NATURE) != null) {
					projects.put(projectKey(project),
							new MosilabProject(project));
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param project
	 * @return
	 */
	private String projectKey(IProject project) {
		return project.getFullPath().toOSString();
	}

	@Override
	public boolean isMosilabProject(IProject project) {
		try {
			return project.getNature(MOSILAB_PROJECT_NATURE) != null;
		} catch (CoreException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<IMosilabProject> getAllMosilabProjects() {
		return new ArrayList<IMosilabProject>(projects.values());
	}

	/**
	 * @param sourceFolder
	 */
	@Override
	public IMosilabSource getMosilabSource(String sourceFolder) {
		IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(sourceFolder));
		if (folder.exists())
			// TODO: refactor this to adaptable from IFolder!
			for (IMosilabProject project : EModelicaPlugin.getDefault()
					.getProjectManager().getAllMosilabProjects())
				for (IMosilabSource src : project.getSrcFolders())
					if (src.getBasePath().equals(folder)) {
						return src;
					}

		return null;
	}

	public static IProjectManager getDefault() {
		return EModelicaPlugin.getDefault().getProjectManager();
	}
}
