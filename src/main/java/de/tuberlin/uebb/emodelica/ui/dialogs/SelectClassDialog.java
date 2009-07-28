/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.dialogs;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.impl.MosilabProjectContentProvider;
import de.tuberlin.uebb.emodelica.model.project.impl.MosilabProjectLabelProvider;

/**
 * @author choeger
 * 
 */
public class SelectClassDialog extends ElementTreeSelectionDialog {
	
	public SelectClassDialog(Shell parent) {
		super(parent, new MosilabProjectLabelProvider(), new MosilabProjectContentProvider(false));
		
		setInput(EModelicaPlugin.getDefault().getProjectManager());
		this.setEmptyListMessage("no source folders found");
		setMessage("&Choose the package:");
	}
}
