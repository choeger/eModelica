/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import de.tuberlin.uebb.emodelica.EModelicaPlugin;
import de.tuberlin.uebb.emodelica.model.project.IModelicaPackage;
import de.tuberlin.uebb.emodelica.model.project.IModelicaResource;
import de.tuberlin.uebb.emodelica.model.project.IMosilabProject;
import de.tuberlin.uebb.emodelica.model.project.IMosilabSource;
import de.tuberlin.uebb.emodelica.operations.NewFragmentCreationOperation;
import de.tuberlin.uebb.emodelica.operations.NewPackageCreationOperation;

/**
 * @author choeger
 * 
 */
public class NewModelicaFragmentWizard extends Wizard implements INewWizard {

	protected NewModelicaFragmentWizardPage page;

	public void addPages() {
		page = new NewModelicaFragmentWizardPage("New Modelica class");
		addPage(page);

		ISelection sel = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getSelection();

		IModelicaResource modelicaResource = EModelicaPlugin.extractSelectionModelicaResource(sel);
		IResource resource = EModelicaPlugin.extractSelectionResource(sel);
	
		/* walk up tree */
		while (modelicaResource != null) {
				if (modelicaResource instanceof IModelicaPackage) {
					page.setPkg((IModelicaPackage) modelicaResource);
				}
				if (modelicaResource instanceof IMosilabSource) {
					page.setSourceDir((IMosilabSource) modelicaResource);
				}
				modelicaResource = modelicaResource.getParent();
			}
		
		if (resource != null) {
			IProject prj = resource.getProject();
			IMosilabProject project = EModelicaPlugin.getDefault()
					.getProjectManager().getMosilabProject(prj);
			if (project != null && project.getSrcFolders().size() > 0 && (page.getDefaultSrc() == null))
					page.setSourceDir(project.getSrcFolders().get(0));
		}
		page.setKind("class");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {

		/* make sure the package does exist */
		WorkspaceModifyOperation createPkg = new NewPackageCreationOperation(
				page.getSourceFolder(), page.getPackageName());

		try {
			createPkg.run(null);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}

		WorkspaceModifyOperation modifyOp = new NewFragmentCreationOperation(
				page.getSourceFolder(), page.getPackageName(), page
						.getTypeName(), page.getFragmentKind());
		try {
			this.getContainer().run(false, false, modifyOp);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public void init(IWorkbench arg0, IStructuredSelection arg1) {
		// TODO Auto-generated method stub
		
	}

}
