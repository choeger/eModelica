package de.tuberlin.uebb.emodelica.preferences;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.ui.MosilabEnvironmentTable;

public class MOSILABPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage, IChangeListener {

	private MosilabEnvironmentTable mosilabEnvironmentTable;

	public MOSILABPreferencePage() {
		super(GRID);
		setPreferenceStore(EModelicaPlugin.getDefault().getPreferenceStore());
		setDescription("General MOSILAB settings");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		initializeDialogUnits(getControl());
		mosilabEnvironmentTable = new MosilabEnvironmentTable(PreferenceConstants.P_MOSILAB_ENVIRONMENTS,"Installed MOSILAB Environments",getFieldEditorParent());
		addField(mosilabEnvironmentTable);
		
		setButtonLayoutData(mosilabEnvironmentTable.getNewButton());
		setButtonLayoutData(mosilabEnvironmentTable.getEditButton());
		setButtonLayoutData(mosilabEnvironmentTable.getDelButton());
		
		mosilabEnvironmentTable.addChangeListener(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	@Override
	public void handleChange(ChangeEvent arg0) {
		setMessage("Configure MOSILAB environments");
		setErrorMessage(null);
		if (mosilabEnvironmentTable.getDefaultEnv() == null) {
			setErrorMessage("select a default environment");
			setValid(false);
		} else
			setValid(true);
	}
}