/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui;

import org.eclipse.swt.widgets.Composite;

/**
 * @author choeger
 *
 */
public interface IUIElement {

	/**
	 * create this UIElements control
	 * @param parent the parent composite
	 * @return a new Composite with all controls added
	 */
	public Composite getControl(Composite parent);
	
}
