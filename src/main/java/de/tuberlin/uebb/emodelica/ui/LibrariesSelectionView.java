/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import de.tuberlin.uebb.emodelica.model.project.ILibraryEntry;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabEnvironment;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.impl.ModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.impl.MosilabProjectContentProvider;
import de.tuberlin.uebb.emodelica.model.project.impl.MosilabProjectLabelProvider;
import de.tuberlin.uebb.emodelica.ui.dialogs.MosilabSelectionDialog;

/**
 * @author choeger
 * 
 */
public class LibrariesSelectionView implements IUIElement {

	private List<ILibraryEntry> libraries = new ArrayList<ILibraryEntry>();
	private TreeViewer treeViewer;
	private Button addButton;
	private Button editButton;
	private Button deleteButton;
	private Shell shell;
	private IMosilabProject project;
	private IMosilabEnvironment environment;
	
	/**
	 * 
	 */
	private class LibraryOnlyContainer extends ModelicaResource {
		private List<Object> children = new ArrayList<Object>();

		public LibraryOnlyContainer() {
			syncChildren();
		}
		
		@Override
		protected void doRefresh() {
			// TODO Auto-generated method stub
		}

		@Override
		public List<? extends Object> getChildren() {
			return children;
		}

		@Override
		public IModelicaResource getParent() {
			return project;
		}

		@Override
		public IResource getResource() {
			return null;
		}

		@Override
		public void syncChildren() {
			children.clear();
			children.add(environment);
			children.addAll(libraries);
		}

		@Override
		public void setParent(IModelicaResource newParent) {
			// TODO Auto-generated method stub
			
		}
	}

	public LibrariesSelectionView(Shell parent, IMosilabProject project) {
		super();
		this.shell = parent;
		this.project = project;
		
		environment = project.getMOSILABEnvironment();
		libraries.addAll(project.getLibraries().getLibraries());
	}

	/**
	 * @return the libraries
	 */
	public List<ILibraryEntry> getLibraries() {
		return libraries;
	}

	/**
	 * @return the environment
	 */
	public IMosilabEnvironment getEnvironment() {
		return environment;
	}

	public Composite getControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);

		treeViewer = new TreeViewer(container);
		treeViewer.setContentProvider(new MosilabProjectContentProvider());
		treeViewer.setLabelProvider(new MosilabProjectLabelProvider());
		treeViewer.setInput(new LibraryOnlyContainer());
		treeViewer.expandAll();

		GridData data = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		data.horizontalSpan = 1;

		treeViewer.getControl().setLayoutData(data);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent arg0) {
				updateEnabled();
			}
		});

		createButtonGroup(container);

		updateEnabled();

		return container;
	}

	private void addNew() {

	}

	private void updateEnabled() {
		ISelection selection = treeViewer.getSelection();
		if (selection.isEmpty()) {
			editButton.setEnabled(false);
			deleteButton.setEnabled(false);
		} else if (selection instanceof IStructuredSelection) {
			IStructuredSelection strSel = (IStructuredSelection) selection;
			if (strSel.size() == 1) {
				editButton.setEnabled(true);
			}
			deleteButton.setEnabled(true);
		}
	}

	private void createButtonGroup(Composite composite) {
		Composite group = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		group.setLayout(layout);

		GridData data = new GridData();
		data.verticalAlignment = SWT.TOP;
		data.grabExcessHorizontalSpace = false;
		data.grabExcessVerticalSpace = true;

		group.setLayoutData(data);

		final GridData buttonData = new GridData();
		buttonData.grabExcessHorizontalSpace = true;

		addButton = new Button(group, SWT.PUSH);
		addButton.setLayoutData(data);
		addButton.setText(JFaceResources.getString("ListEditor.add"));
		addButton.setLayoutData(buttonData);
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
		addButton.pack();

		editButton = new Button(group, SWT.PUSH);
		editButton.setLayoutData(data);
		editButton.setText("&Edit...");
		editButton.setLayoutData(buttonData);
		editButton.pack();
		editButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				editClicked();
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				editClicked();
			}
		});

		deleteButton = new Button(group, SWT.PUSH);
		deleteButton.setLayoutData(data);
		deleteButton.setText(JFaceResources.getString("ListEditor.remove"));
		deleteButton.setLayoutData(buttonData);
		deleteButton.pack();
	}

	protected void editClicked() {
		ISelection selection = treeViewer.getSelection();
		if (!selection.isEmpty() && (selection instanceof IStructuredSelection)) {
			Object selected = ((IStructuredSelection) selection)
					.getFirstElement();
			if (selected instanceof IMosilabEnvironment) {
				MosilabSelectionDialog dlg = new MosilabSelectionDialog(shell,
						(IMosilabEnvironment) selected);
				dlg.open();
				if (dlg.getReturnCode() == MosilabSelectionDialog.OK) {
					environment = dlg.getSelectedEnvironment();
					treeViewer.refresh();
				}
			}
		}
	}

	/**
	 * @return the project
	 */
	public IMosilabProject getProject() {
		return project;
	}

	/**	
	 * @return the addButton
	 */
	public Button getAddButton() {
		return addButton;
	}

	/**
	 * @return the editButton
	 */
	public Button getEditButton() {
		return editButton;
	}

	/**
	 * @return the deleteButton
	 */
	public Button getDeleteButton() {
		return deleteButton;
	}
}
