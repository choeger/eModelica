/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.dialogs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ITreeContentProvider;
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
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;
import de.tuberlin.uebb.emodelica.model.project.impl.MosilabSource;
import de.tuberlin.uebb.emodelica.ui.wizards.NewSourceFolderResourceWizard;

/**
 * @author choeger
 * 
 */
public class SelectNewSourceFolderDialog extends CheckedTreeSelectionDialog {

	private List<IMosilabSource> sources;
	private IContainer rootContainer;

	private Set<IFolder> newFolders = new HashSet<IFolder>();
	
	private class NoSourceFolderFilter extends ViewerFilter {
		@Override
		public boolean select(Viewer arg0, Object parent, Object element) {
			if (element instanceof IFolder) {
				return ((IFolder) element).getAdapter(IModelicaResource.class) == null;
			}
			return true;
		}

	}

	public SelectNewSourceFolderDialog(Shell parent,
			final List<IMosilabSource> sources, IContainer rootContainer) {
		super(parent, new WorkbenchLabelProvider(),
				new WorkbenchContentProvider());

		this.rootContainer = rootContainer;
		setInput(rootContainer);

		this.sources = sources;

		setTitle("Source Folder Selection");
		setMessage("&Select the source folder");

		this.setContainerMode(true);
		this.addFilter(new NoSourceFolderFilter());
		
		this.setValidator(new ISelectionStatusValidator() {
			@Override
			public IStatus validate(Object[] arg0) {
				for (IMosilabSource src : sources)
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

		return parentControl;
	}

	protected void addNew() {
		NewSourceFolderResourceWizard wizard = new NewSourceFolderResourceWizard(rootContainer);
		WizardDialog dialog = new WizardDialog(this.getShell(), wizard);
		int ret = dialog.open();
		System.err.println(ret);
		if (ret == Dialog.OK) {
			IFolder folder = wizard.getSourceFolder();
			try {
				folder.create(true,true,null);
				newFolders.add(folder);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			getTreeViewer().refresh();	
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.CheckedTreeSelectionDialog#cancelPressed()
	 */
	@Override
	protected void cancelPressed() {
		//cleanup
		for (IFolder folder : newFolders)
			try {
				folder.delete(true, null);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		super.cancelPressed();
	}

	@Override
	protected void okPressed() {
		for (Object checked : getTreeViewer().getCheckedElements()) {
			if (checked instanceof IFolder) {
				IFolder folder = (IFolder)checked;
				if (folder.getAdapter(IModelicaResource.class) == null)
						sources.add(new MosilabSource(null,folder));
			}
		}

		super.okPressed();
	}
}
