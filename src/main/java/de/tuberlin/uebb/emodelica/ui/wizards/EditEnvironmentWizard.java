package de.tuberlin.uebb.emodelica.ui.wizards;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;

import de.tuberlin.uebb.emodelica.model.project.IMosilabEnvironment;

public class EditEnvironmentWizard extends Wizard {
	
	private EditEnvironmentWizardPage newEnvPage;
	
	private IMosilabEnvironment environment;

	/**
	 * @return the environment
	 */
	public IMosilabEnvironment getEnvironment() {
		return environment;
	}

	/**
	 * @param environment
	 */
	public EditEnvironmentWizard(IMosilabEnvironment environment) {
		super();
		this.environment = environment;
	}

	@Override
	public void createPageControls(Composite pageContainer) {
		newEnvPage.setMosilabRootStr(environment.mosilabRoot());
		newEnvPage.setNameStr(environment.getName());
		newEnvPage.setMosilacPathStr(environment.compilerCommand());
		super.createPageControls(pageContainer);
	}
	
	@Override
	public void addPages() {
		newEnvPage = new EditEnvironmentWizardPage("edit environment");
		addPage(newEnvPage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		environment.setName(newEnvPage.getNameStr());
		environment.setMosilabRoot(newEnvPage.getMosilabRootStr());
		environment.setCompilerCommand(newEnvPage.getMosilacPathStr());
		return true;
	}

}