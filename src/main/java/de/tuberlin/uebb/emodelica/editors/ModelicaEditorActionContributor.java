/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.texteditor.BasicTextEditorActionContributor;

import de.tuberlin.uebb.emodelica.actions.ViewExperimentActionGroup;

/**
 * @author choeger
 *
 */
public class ModelicaEditorActionContributor extends
		BasicTextEditorActionContributor {
	ViewExperimentActionGroup group;
	private boolean inViewPart;
	
	/**
	 * 
	 */
	public ModelicaEditorActionContributor() {
		
	}

	@Override
	public void setActiveEditor(IEditorPart part) {
		// TODO Auto-generated method stub
		super.setActiveEditor(part);
		
		if (part == null) {
			group = null;
			inViewPart = false;
			return;
		}	
		group = new ViewExperimentActionGroup(part);
		inViewPart = true;
	}

	@Override
	public void init(IActionBars bars, IWorkbenchPage page) {
		// TODO Auto-generated method stub
		super.init(bars, page);
	}

	@Override
	public void contributeToMenu(IMenuManager menu) {
		super.contributeToMenu(menu);
		
		if (inViewPart) {
			group.fillContextMenu(menu);
		}
		
	}

}
