package de.tuberlin.uebb.emodelica.actions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.action.Action;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;

import de.tuberlin.uebb.emodelica.editors.ModelicaEditor;
import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.emodelica.refactoring.IRefactoringDescriptor;
import de.tuberlin.uebb.emodelica.refactoring.ModelicaRefactoring;
import de.tuberlin.uebb.modelica.im.ILocation;

public class RefactoringAction extends Action {

	private IRefactoringDescriptor fDescriptor;
	private RefactoringWizard fWizard;
	private ModelicaEditor fEditor;
	private ModelicaRefactoring fRefactoring;
	private Class<RefactoringWizard> fWizardClass;

	public RefactoringAction(IRefactoringDescriptor descriptor, ModelicaRefactoring ref, Class<RefactoringWizard> wizardClass
			, ModelicaEditor editor) {
		this.fDescriptor = descriptor;
		this.fWizardClass = wizardClass;
		this.fEditor = editor;
		this.fRefactoring = ref;
		
		this.setText(descriptor.getName());
	}

	public ModelicaRefactoring getfRefactoring() {
		return fRefactoring;
	}

	public void update(ILocation selection, Model model) {
		setEnabled(fDescriptor.isValidFor(selection));
		fRefactoring.setModel(model);
		fRefactoring.setLocation(selection);
	}

	@Override
	public void run() {
		super.run();
		try { 
		
		Constructor<?> constr = fWizardClass.getConstructor(fRefactoring.getClass(), Integer.TYPE);
		
		RefactoringWizard wizard = (RefactoringWizard) constr.newInstance(fRefactoring, RefactoringWizard.DIALOG_BASED_USER_INTERFACE);

		RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(wizard); 
			String titleForFailedChecks = ""; //$NON-NLS-1$ 
			op.run(fEditor.getEditorSite().getShell(), titleForFailedChecks ); 
		} catch( InterruptedException irex ) { 
			// operation was cancelled 
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
