/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui;


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

import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.ui.dialogs.SelectNewSourceFolderDialog;
import de.tuberlin.uebb.emodelica.ui.dialogs.SourcePathContentProvider;
import de.tuberlin.uebb.emodelica.ui.dialogs.SourcePathLabelProvider;

/**
 * @author choeger
 *
 */
public class SourceFolderSelectionView implements IUIElement {

	private IMosilabProject project;
	private TreeViewer treeViewer;
	private Button addButton;
	private Button editButton;
	private Button deleteButton;
	private Shell shell;
	
	public SourceFolderSelectionView(Shell parent, IMosilabProject project) {
		super();
		this.shell = parent;
		this.project = project;
	}

	public Composite getControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);
		
		treeViewer = new TreeViewer(container);
		treeViewer.setContentProvider(new SourcePathContentProvider());
		treeViewer.setLabelProvider(new SourcePathLabelProvider());
		treeViewer.setInput(project);
		treeViewer.expandAll();		
		
		GridData data = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		data.horizontalSpan = 1;
		
		treeViewer.getControl().setLayoutData(data);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent arg0) {
				updateEnabled();				
			}});
		
		createButtonGroup(container);		
		
		updateEnabled();
		
		return container;
	}
	
	private void addNew() {
		SelectNewSourceFolderDialog dlg = new  SelectNewSourceFolderDialog(shell, project);
		dlg.open();
		treeViewer.setInput(project);
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
		addButton.setText("&Add Folder...");
		addButton.setLayoutData(buttonData);
		addButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				addNew();
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				addNew();
			}});
		addButton.pack();
		
		editButton = new Button(group, SWT.PUSH);
		editButton.setLayoutData(data);
		editButton.setText("&Edit...");
		editButton.setLayoutData(buttonData);
		editButton.pack();
		
		deleteButton = new Button(group, SWT.PUSH);
		deleteButton.setLayoutData(data);
		deleteButton.setText("&Remove");
		deleteButton.setLayoutData(buttonData);
		deleteButton.pack();
	}
	
}
