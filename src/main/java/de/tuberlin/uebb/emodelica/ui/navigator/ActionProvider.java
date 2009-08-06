package de.tuberlin.uebb.emodelica.ui.navigator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.SubMenuManager;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

import de.tuberlin.uebb.emodelica.actions.CCPActionGroup;
import de.tuberlin.uebb.emodelica.actions.ViewExperimentActionGroup;
import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;

public class ActionProvider extends CommonActionProvider {

	private CCPActionGroup ccpGroup;
	private ViewExperimentActionGroup viewExperimentActionGroup;
	private boolean inViewPart;
	
	public ActionProvider() {

	}

	public void init(ICommonActionExtensionSite site) {

		ICommonViewerWorkbenchSite workbenchSite = null;
		if (site.getViewSite() instanceof ICommonViewerWorkbenchSite)
			workbenchSite = (ICommonViewerWorkbenchSite) site.getViewSite();

		if (workbenchSite != null) {
			if (workbenchSite.getPart() != null
					&& workbenchSite.getPart() instanceof IViewPart) {
				IViewPart viewPart = (IViewPart) workbenchSite.getPart();
				
				ccpGroup = new CCPActionGroup(viewPart);
				viewExperimentActionGroup = new ViewExperimentActionGroup(viewPart);
				inViewPart = true;
			}
		}
	}

	public void fillContextMenu(IMenuManager menu) {
		if (inViewPart) {
			System.err.println("filling context menu");
			MenuManager viewIn = new MenuManager("View in");
			viewIn.setVisible(true);
			viewExperimentActionGroup.fillContextMenu(viewIn);
			menu.add(viewIn);
			menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			menu.setVisible(true);
			ccpGroup.fillContextMenu(menu);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.ActionGroup#setContext(org.eclipse.ui.actions.ActionContext)
	 */
	@Override
	public void setContext(ActionContext context) {
		super.setContext(context);
		viewExperimentActionGroup.setContext(context);
		ccpGroup.setContext(context);
	}

}
