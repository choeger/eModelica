/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.wizards;

import java.util.List;

import org.eclipse.jface.wizard.Wizard;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.IMosilabEnvironment;
import de.tuberlin.uebb.emodelica.model.project.impl.MosilabEnvironment;

/**
 * @author choeger
 * 
 */
public class NewEnvironmentWizard extends Wizard {
	
	private EditEnvironmentWizardPage newEnvPage = new EditEnvironmentWizardPage(
			"add environment");
	
	private List<IMosilabEnvironment> environments;

	public NewEnvironmentWizard(List<IMosilabEnvironment> environments) {
		this.environments = environments;
	}

	@Override
	public void addPages() {
		addPage(newEnvPage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		MosilabEnvironment env = 
			new MosilabEnvironment(newEnvPage.getMosilabRoot().getText(),
				newEnvPage.getNameField().getText());
		env.setCompilerCommand(newEnvPage.getMosilacPath().getText());
		environments.add(env);
		return true;
	}

}
