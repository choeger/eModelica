package de.tuberlin.uebb.emodelica.ui.navigator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

import de.tuberlin.uebb.emodelica.actions.CCPActionGroup;

public class ActionProvider extends CommonActionProvider {

	private CCPActionGroup ccpGroup;
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
				inViewPart = true;
			}
		}
	}

	public void fillContextMenu(IMenuManager menu) {
		if (inViewPart) {
			ccpGroup.fillContextMenu(menu);
		}
	}

}
