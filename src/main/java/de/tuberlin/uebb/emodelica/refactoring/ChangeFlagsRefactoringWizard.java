/**
 * 
 */
package de.tuberlin.uebb.emodelica.refactoring;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

/**
 * @author choeger
 *
 */
public class ChangeFlagsRefactoringWizard extends RefactoringWizard {
	
	private ChangeFlagsRefactoring refactoring;
	
	/**
	 * @param refactoring
	 * @param flags
	 */
	public ChangeFlagsRefactoringWizard(ChangeFlagsRefactoring refactoring, int flags) {
		super(refactoring, flags);
		this.refactoring = refactoring;
		
	}

	@Override
	protected void addUserInputPages() {
		// TODO Auto-generated method stub
		this.addPage(new ChangeFlagsWizardPage("Set Flags", refactoring));
	}
	
	@Override
	public boolean performCancel() {
		return super.performCancel();
	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return super.performFinish();
	}
}
