/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.dialogs;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.impl.PathConfigContentProvider;
import de.tuberlin.uebb.emodelica.model.project.impl.SourcePathLabelProvider;

/**
 * @author choeger
 * 
 */
public class SelectSourceFolderDialog extends ElementTreeSelectionDialog {
	
	public SelectSourceFolderDialog(Shell parent) {
		super(parent, new SourcePathLabelProvider(), new PathConfigContentProvider());
		this.setEmptyListMessage("currently there are no MOSILAB projects configured in the workspace");
		setInput(EModelicaPlugin.getDefault().getProjectManager());
		setMessage("&Choose the source folder:");
	}
}
