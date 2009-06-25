/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.dialogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;
import de.tuberlin.uebb.emodelica.ui.wizards.NewSourceFolderResourceWizard;

/**
 * @author choeger
 * 
 */
public class SelectNewSourceFolderDialog extends CheckedTreeSelectionDialog {

	private IMosilabProject project;
	private List<IFolder> createdFolders = new ArrayList<IFolder>();

	private class NoSourceFolderFilter extends ViewerFilter {
		@Override
		public boolean select(Viewer arg0, Object parent, Object element) {
			if (element instanceof IProject)
				return true;

			if (element instanceof IFolder) {
				return true;
			}
			return false;
		}

	}

	public SelectNewSourceFolderDialog(Shell parent, final IMosilabProject project) {
		super(parent, new WorkbenchLabelProvider(),
				new WorkbenchContentProvider());
		setInput(project.getProject());
		setTitle("Source Folder Selection");
		setMessage("&Select the source folder");
		this.project = project;
		this.setContainerMode(true);
		this.setValidator(new ISelectionStatusValidator() {
			@Override
			public IStatus validate(Object[] arg0) {
				for (IMosilabSource src : project.getSrcFolders())
					getTreeViewer().setGrayChecked(src.getBasePath(), true);
				
				return new Status(IStatus.OK, EModelicaPlugin.PLUGIN_ID, "");
			}
			
		});
	}

	@Override
	protected Composite createSelectionButtons(Composite parent) {
		Composite parentControl = (Composite) super
				.createSelectionButtons(parent);
		Button addButton = new Button(parentControl, SWT.PUSH);
		addButton.setText("Create &New Folder...");
		addButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				addNew();
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				addNew();
			}
		});
		this.getTreeViewer().addFilter(new NoSourceFolderFilter());
		
		return parentControl;
	}

	protected void addNew() {
		NewSourceFolderResourceWizard wizard = new NewSourceFolderResourceWizard(project);
		WizardDialog dialog = new WizardDialog(this.getShell(), wizard);
		int ret = dialog.open();
		System.err.println(ret);
		if (ret == Dialog.OK) {
			IFolder folder = wizard.getSourceFolder();
			System.err.println("creating new folder '" + folder + "'");
			try {
				folder.create(true, true, null);
			} catch (CoreException e) {
				e.printStackTrace();
				return;
			}
			createdFolders.add(folder);
			getTreeViewer().refresh();
		}
	}
	
	@Override
	protected void cancelPressed() {
		for (IFolder folder : createdFolders)
			try {
				folder.delete(true, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		super.cancelPressed();
	}
	
	@Override
	protected void okPressed() {
		project.getSrcFolders().clear();
		
		IStructuredSelection selection = (IStructuredSelection) this.getTreeViewer().getSelection();
		for (Iterator<?> iter = selection.iterator();iter.hasNext();) {
			Object selected = iter.next();
			if (selected instanceof IFolder)
				project.addSrc(((IFolder)selected).getName());
		}
			
		project.syncChildren();
		super.okPressed();
	}
}
