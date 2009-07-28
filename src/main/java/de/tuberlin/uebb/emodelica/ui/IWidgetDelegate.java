/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * @author choeger
 *
 */
public interface IWidgetDelegate {

	/**
	 * Create a button with default Values
	 * @param label
	 * @return the button
	 */
	public Button createButton(Composite parent, String label);
}
