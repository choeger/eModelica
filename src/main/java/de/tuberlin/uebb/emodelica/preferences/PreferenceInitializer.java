package de.tuberlin.uebb.emodelica.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = EModelicaPlugin.getDefault().getPreferenceStore();
	
		store.setValue(PreferenceConstants.P_MOSILAB_DEFAULT_OUTFOLDER, "c++");
	}

}
