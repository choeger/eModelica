/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
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
import de.tuberlin.uebb.emodelica.operations.NewPackageCreationOperation;

/**
 * @author choeger
 * 
 */
public class NewPackageWizard extends Wizard implements INewWizard {

	NewPackageWizardPage page = new NewPackageWizardPage("create package");

	@Override
	public void addPages() {
		ISelection sel = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getSelection();

		IModelicaResource modelicaResource = EModelicaPlugin.extractSelectionModelicaResource(sel);
		IResource resource = EModelicaPlugin.extractSelectionResource(sel);
	
		System.err.println("[SELECTION] " + modelicaResource + "," + resource);
		
		/* walk up tree */
		while (modelicaResource != null) {
				if (modelicaResource instanceof IModelicaPackage) {
					page.setParentPkg((IModelicaPackage) modelicaResource);
				}
				if (modelicaResource instanceof IMosilabSource) {
					page.setSrcPath((IMosilabSource) modelicaResource);
				}
				modelicaResource = modelicaResource.getParent();
			}
		
		if (resource != null) {
			IProject prj = resource.getProject();
			IMosilabProject project = EModelicaPlugin.getDefault().getProjectManager().getMosilabProject(prj); 
			if (project != null && project.getSrcFolders().size() > 0 && (page.getSrcPath() == null))
				page.setSrcPath(project.getSrcFolders().get(0));
		}

		addPage(page);
	}

	@Override
	public boolean performFinish() {
		WorkspaceModifyOperation newPackageOp = new 
			NewPackageCreationOperation(page.getSourceFolder(), page.getPackageName());
		
		try {
			newPackageOp.run(null);
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
