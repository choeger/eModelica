/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors.presentation;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;

/**
 * @author choeger
 * 
 */
public class HighlightingPosition extends Position {

	private TextAttribute textStyle;

	public HighlightingPosition(int offset, int length, TextAttribute textStyle) {
		super(offset, length);
		this.textStyle = textStyle;
	}

	/**
	 * @return the styleRange
	 */
	public StyleRange getStyleRange() {
		final int style = textStyle.getStyle();
		int fontStyle = style
				& (SWT.ITALIC | SWT.BOLD | SWT.NORMAL);
		StyleRange styleRange = new StyleRange(offset, length, textStyle
				.getForeground(), textStyle.getBackground(), fontStyle);
		styleRange.strikeout = (style & TextAttribute.STRIKETHROUGH) != 0;
		styleRange.underline = (style & TextAttribute.UNDERLINE) != 0;
		return styleRange;
	}
}
