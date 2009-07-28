/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * @author choeger
 * 
 */
public class TextButtonGroup {

	private Text text = null;
	private Button button = null;
	private Composite parent;
	private String buttonText;
	private IWidgetDelegate delegate;

	public TextButtonGroup(Composite parent, String buttonText, IWidgetDelegate delegate) {
		this.parent = parent;
		this.buttonText = buttonText;
		this.delegate = delegate;
		initialize();
	}
	
	private void initialize() {
		text = new Text(parent, SWT.BORDER);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		button = delegate.createButton(parent, buttonText);
		button.pack();
		
		text.pack();
	}

	/**
	 * @return the text
	 */
	public Text getText() {
		return text;
	}

	/**
	 * @return the button
	 */
	public Button getButton() {
		return button;
	}

	/**
	 * @return the parent
	 */
	public Composite getParent() {
		return parent;
	}
}
