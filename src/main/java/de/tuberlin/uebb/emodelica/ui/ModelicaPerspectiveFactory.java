/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * @author choeger
 * 
 */
public class ModelicaPerspectiveFactory implements IPerspectiveFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui
	 * .IPageLayout)
	 */
	@Override
	public void createInitialLayout(IPageLayout layout) {
		// Add "new wizards".
		layout
				.addNewWizardShortcut("de.tuberlin.uebb.emodelica.wizards.newprojectwizard");
		layout
				.addNewWizardShortcut("de.tuberlin.uebb.emodelica.wizards.newpackagewizard");
		layout
				.addNewWizardShortcut("de.tuberlin.uebb.emodelica.wizards.newelementwizard");

		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");

		// Add "show views".
		layout.addShowViewShortcut("org.eclipse.ui.navigator.ProjectExplorer");
		layout.addShowViewShortcut(IPageLayout.ID_BOOKMARKS);
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
		layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
		layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);

		IFolderLayout right = layout.createFolder("right", IPageLayout.RIGHT,
				(float) 0.8, layout.getEditorArea());
		right.addView(IPageLayout.ID_OUTLINE);

		layout.addView("org.eclipse.ui.navigator.ProjectExplorer", IPageLayout.LEFT, 0.2f, layout
				.getEditorArea());

		IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM,
				(float) 0.8, layout.getEditorArea());
		bottom.addView(IPageLayout.ID_OUTLINE);
		bottom.addView(IPageLayout.ID_PROBLEM_VIEW);

		layout.addActionSet("de.tuberlin.uebb.emodelica.actions.actionSet1");
		layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
	}

}
