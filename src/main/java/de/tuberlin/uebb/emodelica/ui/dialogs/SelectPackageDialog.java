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
public class SelectPackageDialog extends ElementTreeSelectionDialog {
	
	public SelectPackageDialog(Shell parent, String sourceFolder) {
		super(parent, new MosilabProjectLabelProvider(), new MosilabProjectContentProvider());
		setInput(EModelicaPlugin.getDefault().getProjectManager().getMosilabSource(sourceFolder));
		this.setEmptyListMessage("no valid packages found");
		setMessage("&Choose the package:");
	}
}
