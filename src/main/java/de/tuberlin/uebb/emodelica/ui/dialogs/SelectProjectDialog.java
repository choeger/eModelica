/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.dialogs;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;

/**
 * @author choeger
 * eclipse really should have that one!
 */
public class SelectProjectDialog extends ElementTreeSelectionDialog {
	
	public SelectProjectDialog(Shell parent) {
		//SourcePathLabelProvider is a subset of what we need, see Ticket #29
		super(parent, new SourcePathLabelProvider(), new MosilabProjectContentProvider());
		this.setEmptyListMessage("currently there are no MOSILAB projects configured in the workspace");
		setInput(EModelicaPlugin.getDefault().getProjectManager());
		setMessage("&Choose the MOSILAB project:");
	}
	
	/**
	 * convenience method  
	 * @return the selection as IMosilabProject
	 */
	public IMosilabProject getProject() {
		return (IMosilabProject) this.getFirstResult();
	}
}
