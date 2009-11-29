/**
 * 
 */
package de.tuberlin.uebb.emodelica.refactoring;

import de.tuberlin.uebb.modelica.im.ILocation;
import de.tuberlin.uebb.modelica.im.nodes.IClassNode;
import de.tuberlin.uebb.modelica.im.nodes.IVarDefNode;

/**
 * @author choeger
 *
 */
public class ChangeFlagsRefactoringDescriptor implements IRefactoringDescriptor {

	private static final String CHANGE_VISIBILITY = "Change visibility";

	/**
	 * 
	 */
	public ChangeFlagsRefactoringDescriptor() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.refactoring.IRefactoringDescriptor#getDescription(de.tuberlin.uebb.modelica.im.ILocation)
	 */
	@Override
	public String getDescription(ILocation location) {
		return CHANGE_VISIBILITY;
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.refactoring.IRefactoringDescriptor#getName()
	 */
	@Override
	public String getName() {
		return CHANGE_VISIBILITY;
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.refactoring.IRefactoringDescriptor#isValidFor(de.tuberlin.uebb.modelica.im.ILocation)
	 */
	@Override
	public boolean isValidFor(ILocation selected) {
		return (selected instanceof IVarDefNode || selected instanceof IClassNode);
	}

}
