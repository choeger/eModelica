/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.IStaleListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.emodelica.model.project.IMosilabEnvironment;
import de.tuberlin.uebb.emodelica.ui.wizards.EditEnvironmentWizard;
import de.tuberlin.uebb.emodelica.ui.wizards.NewEnvironmentWizard;

/**
 * @author choeger
 *
 */
public class MosilabEnvironmentTable extends FieldEditor implements IObservable {
	
	/**
	 * @return the newButton
	 */
	public Button getNewButton() {
		return newButton;
	}

	/**
	 * @return the delButton
	 */
	public Button getDelButton() {
		return delButton;
	}

	/**
	 * @return the editButton
	 */
	public Button getEditButton() {
		return editButton;
	}

	private IMosilabEnvironment selectedEnv = null;
	private Set<IChangeListener> listeners = new HashSet<IChangeListener>();
	
	class TableSelectionListener implements SelectionListener {

		private final void onSelect(SelectionEvent event) {
			if (event.detail == SWT.CHECK) {
				TableItem tItem = (TableItem)event.item;
				IMosilabEnvironment env = (IMosilabEnvironment) tItem.getData();

				//clear default entry
				if (selectedEnv != null)
					selectedEnv.setDefault(false);
				
				selectedEnv = null;
				
				env.setDefault(tItem.getChecked());
				updateTableItems();
				notifyAllListeners();
			}
				
			boolean enabled = table.getSelectionIndex() != -1;
			editButton.setEnabled(enabled);
			delButton.setEnabled(enabled);
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent event) {
			onSelect(event);
		}

		@Override
		public void widgetSelected(SelectionEvent event) {
			System.err.println("widgetDefaultSelected");
			onSelect(event);
		}
		
	}
	
	class NewButtonSelectionListener implements SelectionListener {

		private final void onClicked() {
			NewEnvironmentWizard wizard = new NewEnvironmentWizard(environments);
			WizardDialog dialog = new WizardDialog(Display.getCurrent().getActiveShell(), wizard);
			int ret = dialog.open();
			if (ret == Window.OK) {
				updateTableItems();
				notifyAllListeners();
			}
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			onClicked();
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			onClicked();
		}
	}

	class DelButtonSelectionListener implements SelectionListener {

		private final void onClicked() {
			int index = table.getSelectionIndex();
			environments.remove(index);
			updateTableItems();
			notifyAllListeners();
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			onClicked();
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			onClicked();
		}
	}
	
	class EditButtonSelectionListener implements SelectionListener {

		private final void onClicked() {
			int index = table.getSelectionIndex();
			System.err.println("editing: " + index);
			if (index >= 0) {
				EditEnvironmentWizard wizard = new EditEnvironmentWizard(environments.get(index));
				WizardDialog dialog = new WizardDialog(Display.getCurrent().getActiveShell(), wizard);
				int ret = dialog.open();
				if (ret == Window.OK) {
					updateTableItems();
					notifyAllListeners();
				}
			}
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			onClicked();
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			onClicked();
		}
	}
	
	private Table table;
	private Button newButton;
	private Button delButton;
	private Button editButton;
	private List<IMosilabEnvironment> environments = new ArrayList<IMosilabEnvironment>();
	
	public MosilabEnvironmentTable(String name, String labelText,
			Composite fieldEditorParent) {
		super(name, labelText, fieldEditorParent);
	}

	@Override
	protected void adjustForNumColumns(int arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		System.err.println("doFillIntoGrid. " + numColumns);
		Composite composite = parent;
		
		createEnvironmentWidgets(composite);
		
		SelectionListener tableSelectionListener = new TableSelectionListener();
		table.addSelectionListener(tableSelectionListener);
		
		SelectionListener newButtonSelectionListener = new NewButtonSelectionListener();
		newButton.addSelectionListener(newButtonSelectionListener);
	}

	/**
	 * @param composite
	 */
	private void createEnvironmentWidgets(Composite composite) {
		final GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		GridData compositeData = new GridData();
		compositeData.grabExcessHorizontalSpace = true;
		composite.setLayoutData(compositeData);
		
		final Label label1 = new Label(composite, SWT.NONE);
		final GridData gridDataLabel1 = new GridData();
		gridDataLabel1.horizontalSpan = 2;
		label1.setLayoutData(gridDataLabel1);
		label1.setText(this.getLabelText());
		
		table = new Table(composite,SWT.CHECK | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		
		TableColumn nameColumn = new TableColumn(table, SWT.NONE);
		nameColumn.setText("Name");
		nameColumn.pack();
		TableColumn locaColumn = new TableColumn(table, SWT.NONE);
		locaColumn.setText("Location");
		locaColumn.pack();
		
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		GridData dataTable = new GridData(SWT.FILL, SWT.FILL, true, true);
		dataTable.horizontalSpan = 1;
		dataTable.heightHint = 200;
		dataTable.grabExcessHorizontalSpace = true;
		table.setLayoutData(dataTable);
		
		Composite buttonGroup = new Composite(composite, SWT.NONE);
		GridLayout buttonLayout = new GridLayout();
		buttonLayout.numColumns = 1;
		buttonGroup.setLayout(buttonLayout);
		
		final GridData buttonGroupData = new GridData();
		buttonGroupData.verticalAlignment = SWT.TOP;
		buttonGroupData.grabExcessHorizontalSpace = false;
		buttonGroup.setLayoutData(buttonGroupData);
		
		final GridData buttonData = new GridData();
		buttonData.grabExcessHorizontalSpace = true;
		
		newButton = new Button(buttonGroup, SWT.PUSH);
		newButton.setText("&New...");
		newButton.setLayoutData(buttonData);
		
		editButton = new Button(buttonGroup, SWT.PUSH);
		editButton.setText("&Edit...");
		editButton.setLayoutData(buttonData);
		editButton.addSelectionListener(new EditButtonSelectionListener());
		editButton.setEnabled(false);
		
		delButton = new Button(buttonGroup, SWT.PUSH);
		delButton.setText("&Remove");
		delButton.setLayoutData(buttonData);
		delButton.addSelectionListener(new DelButtonSelectionListener());
		delButton.setEnabled(false);
	}

	@Override
	protected void doLoad() {
		syncHere();
		updateTableItems();		
	}

	@Override
	protected void doLoadDefault() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void doStore() {
		System.err.println("doStore()");
		syncBack();
		EModelicaPlugin.getDefault().storeMosilabSettings();
	}

	@Override
	public int getNumberOfControls() {
		return 2;
	}

	private void syncHere() {
		environments.clear();
		environments.addAll(EModelicaPlugin.getDefault().getMosilabEnvironments());
	}
	
	private void syncBack() {
		System.err.println("syncBack()");

		EModelicaPlugin.getDefault().getMosilabEnvironments().clear();
		EModelicaPlugin.getDefault().getMosilabEnvironments().addAll(environments);
	}
	
	private void updateTableItems() {
		/* Brute force syncing should be no problem, expect very little amount of entries */
		table.removeAll();
		//table.setItemCount(environments.size());
		for (IMosilabEnvironment env : environments) {
			TableItem item = new TableItem(table, SWT.NONE);
			setTableItemData(item, env);
			if (env.isDefault())
				selectedEnv = env;
		}
		
		for (TableColumn column : table.getColumns())
			column.pack();
	}

	private void setTableItemData(TableItem item,
			IMosilabEnvironment env) {
		item.setText(0, env.getName());
		item.setText(1, env.mosilabRoot());
		item.setImage(0, Images.LIBRARY_FOLDER_DESCRIPTOR.createImage());
		item.setChecked(env.isDefault());
		item.setData(env);
	}

	public void notifyAllListeners() {
		for (IChangeListener listener : listeners) {
			listener.handleChange(new ChangeEvent(this));
		}
	}
	
	@Override
	public void addChangeListener(
			org.eclipse.core.databinding.observable.IChangeListener arg0) {
		listeners.add(arg0);		
	}

	@Override
	public void addStaleListener(IStaleListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Realm getRealm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isStale() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeChangeListener(
			org.eclipse.core.databinding.observable.IChangeListener arg0) {
		this.listeners.remove(arg0);
	}

	@Override
	public void removeStaleListener(IStaleListener arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * @return the defaultEnv
	 */
	public IMosilabEnvironment getDefaultEnv() {
		return selectedEnv;
	}

	/**
	 * @return the environments
	 */
	public List<IMosilabEnvironment> getEnvironments() {
		return environments;
	}

}
