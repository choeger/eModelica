/**
 * 
 */
package de.tuberlin.uebb.emodelica.refactoring;

import de.tuberlin.uebb.modelica.im.ILocation;

/**
 * @author choeger
 *
 */
public interface IRefactoringDescriptor {

	public boolean isValidFor(ILocation selected);
	
	public String getName();
	
	public String getDescription(ILocation location);
}
