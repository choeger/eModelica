package de.tuberlin.uebb.emodelica.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;


public class ViewExperimentActionGroup extends ActionGroup {
	private IWorkbenchSite site;
	private List<ViewExperimentAction> actions = new ArrayList<ViewExperimentAction>();

	public ViewExperimentActionGroup(IWorkbenchPartSite site) {
		this.site = site;
		
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		
		IExtensionPoint point = reg.getExtensionPoint("de.tuberlin.uebb.emodelica.experimentView");
		
		for (IExtension extension : point.getExtensions()) {
			
			for (IConfigurationElement element : extension.getConfigurationElements()) {
				if (element.getName().equals("Viewer")) {
					String viewID = element.getAttribute("View");
					String iconPath = element.getAttribute("icon");
					String name = element.getAttribute("Name");

					ImageDescriptor icon = EModelicaPlugin.
						imageDescriptorFromPlugin(element.getContributor().getName(), iconPath);
					
					ViewExperimentAction action = new ViewExperimentAction(name, viewID, icon, site);
					actions.add(action);
				}
			}	
		}	
	}
	
	public ViewExperimentActionGroup(IViewPart viewPart) {
		this(viewPart.getSite());
	}
	
	public ViewExperimentActionGroup(IEditorPart editorPart) {
		this(editorPart.getSite());
	}

	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		
		if (getContext().getSelection() instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection)getContext().getSelection();
			if (selection.getFirstElement() instanceof IExperiment)
			for (ViewExperimentAction action : actions) {
				action.setExperiment((IExperiment) selection.getFirstElement());
				menu.add(action);
			}
		}
	}
}
