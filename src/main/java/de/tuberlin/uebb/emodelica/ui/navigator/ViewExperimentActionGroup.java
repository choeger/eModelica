package de.tuberlin.uebb.emodelica.ui.navigator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;

import de.tuberlin.uebb.emodelica.actions.ViewExperimentAction;

public class ViewExperimentActionGroup extends ActionGroup {
	private IWorkbenchSite site;
	private List<Action> actions = new ArrayList<Action>();

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
					
					Action action = new ViewExperimentAction(name, viewID, iconPath, site);
					actions.add(action);
				}
			}	
		}	
	}
	
	public ViewExperimentActionGroup(IViewPart viewPart) {
		this(viewPart.getSite());
	}

	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		for (Action action : actions) {
			menu.add(action);
		}
	}
}
