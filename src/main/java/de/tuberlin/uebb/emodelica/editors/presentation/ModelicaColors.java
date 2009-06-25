/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors.presentation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * @author choeger
 *
 */
public class ModelicaColors {
	//TODO: move to configuration
	public static Color foregroundColor = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	public static Color commentColor = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN);
	public static Color stringColor = Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
	public static Color kwTokenColor = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED);
	public static Color stringCommentColor = Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);


}
