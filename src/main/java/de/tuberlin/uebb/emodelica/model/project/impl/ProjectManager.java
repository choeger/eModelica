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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

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
				System.err.println("delta for project! " + delta.getKind());
				IProject project = (IProject) delta.getResource();
				String path = project.getFullPath().toOSString();

				if (delta.getKind() == IResourceDelta.OPEN) {
					if (project.isOpen()) {
						/* new opened MOSILAB project */
						if (project.getNature(MOSILAB_PROJECT_NATURE) != null) {
							System.err.println("adding newly opened " + path);
							projects.put(path, new MosilabProject(project));
						}
					} else {
						/* closed MOSILAB project */
						System.err.println("removing closed " + path);
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
				System.err.println("For: " + delta.getResource() + "Resource: "
						+ modelicaResource);
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
		String path = eclipseProject.getFullPath().toOSString();
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
			System.err.println("Resource changed! " + event.getSource() + " "
					+ event.getType());
			if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
				IProject project = (IProject) event.getResource();
				if (project.getNature(MOSILAB_PROJECT_NATURE) != null) {
					IMosilabProject mpj = getMosilabProject(project);
					mpj.writeBackPropertiesGuarded();
				}
			} else if (event.getDelta() != null) {
				event.getDelta().accept(visitor);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() {
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot()
				.getProjects()) {
			try {
				if (project.isOpen()
						&& project.getNature(MOSILAB_PROJECT_NATURE) != null) {
					projects.put(project.getFullPath().toOSString(),
							new MosilabProject(project));
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
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
