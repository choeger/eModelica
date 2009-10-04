/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.widgets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.IMosilabEnvironment;
import de.tuberlin.uebb.emodelica.preferences.MOSILABPreferencePage;

/**
 * @author choeger
 *
 */
public class MosilabSelection {

	private Button useDefaultEnvironment;
	private Button openInstalledEnvironments;
	private Button useAlternateEnvironment;
	private Combo alternateEnvironmentSelection;
	private Set<ISelectionChangedListener> listeners = new HashSet<ISelectionChangedListener>();
	private Composite parent;
	private Button useNoEnvironment; 
	
	/**
	 * @return the useDefaultEnvironment
	 */
	public Button getUseDefaultEnvironment() {
		return useDefaultEnvironment;
	}

	/**
	 * @return the alternateEnvironmentSelection
	 */
	public Combo getAlternateEnvironmentSelection() {
		return alternateEnvironmentSelection;
	}

	public MosilabSelection(Composite parent, int style) {
		this.parent = parent;
		Group group = new Group(parent, style);
		
		group.setText("MOSILAB installation");
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		group.setLayout(gridLayout);

		final GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 3;
		group.setLayoutData(gridData);
		
		useDefaultEnvironment = new Button(group, SWT.RADIO);
		final GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalSpan = 3;
		useDefaultEnvironment.setLayoutData(layoutData);
		useDefaultEnvironment.pack();
		
		updateDefaultEnvironment();
		
		useAlternateEnvironment = new Button(group, SWT.RADIO);
		useAlternateEnvironment.setText("Alternate Environment");
		
		SelectionListener listener = new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				updateWidgetAndNotifyListeners();
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				updateWidgetAndNotifyListeners();
			}
						
		};
		
		alternateEnvironmentSelection = new Combo(group,SWT.READ_ONLY);
		
		openInstalledEnvironments = new Button(group, SWT.PUSH);
		openInstalledEnvironments.setText("&Environments...");

		useDefaultEnvironment.addSelectionListener(listener);
		useAlternateEnvironment.addSelectionListener(listener);
		alternateEnvironmentSelection.addSelectionListener(listener);
		setupEnvironments();
		
		openInstalledEnvironments.addSelectionListener(new SelectionListener() {
		
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				openPreferencePage();
				setupEnvironments();
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				openPreferencePage();
				setupEnvironments();
			}});
		
		useNoEnvironment = new Button(group, SWT.RADIO);
		useNoEnvironment.setLayoutData(layoutData);
		useNoEnvironment.setText("&No MOSILAB");
	}

	private void openPreferencePage() {
		IPreferencePage page = new MOSILABPreferencePage();
		PreferenceManager mgr = new PreferenceManager();
		IPreferenceNode node = new PreferenceNode("1", page);
		mgr.addToRoot(node);
		PreferenceDialog dialog = new PreferenceDialog(parent.getShell(), mgr);
		dialog.create();
		dialog.setMessage(page.getTitle());
		dialog.open();
		updateDefaultEnvironment();
		updateWidgetAndNotifyListeners();
	}
	
	public void updateDefaultEnvironment() {
		String environmentName = "none";
		if (EModelicaPlugin.getDefault().getDefaultMosilabEnvironment() != null)
			environmentName = EModelicaPlugin.getDefault().getDefaultMosilabEnvironment().getName();
		
		useDefaultEnvironment.setText("use default installation (Currently '" + 
				environmentName + "')");
	}
	
	/**
	 * @return the openInstalledEnvironments
	 */
	public Button getOpenInstalledEnvironments() {
		return openInstalledEnvironments;
	}

	/**
	 * @return the useAlternateEnvironment
	 */
	public Button getUseAlternateEnvironment() {
		return useAlternateEnvironment;
	}
	
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		this.listeners.remove(listener);
	}

	private void updateWidgetAndNotifyListeners() {
		alternateEnvironmentSelection.setEnabled(useAlternateEnvironment.getSelection());
		
		for (ISelectionChangedListener listener : listeners)
			listener.selectionChanged(null);
	}
	
	private void setupEnvironments() {
		final List<IMosilabEnvironment> mosilabEnvironments = EModelicaPlugin.getDefault().getMosilabEnvironments();
		final String[] items = new String[mosilabEnvironments.size()];
		for (int i = 0; i < mosilabEnvironments.size();i++) {
			items[i] = mosilabEnvironments.get(i).getName();
		}
		alternateEnvironmentSelection.setItems(items);
		
		if (mosilabEnvironments.size() > 0)
			alternateEnvironmentSelection.select(0);
	}
}
