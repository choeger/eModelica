/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui;

import org.eclipse.jface.viewers.CellEditor.LayoutData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author choeger
 * 
 */
public class ButtonTextButtonGroup {

	private Button button2 = null;
	private Text text = null;
	private Button button1 = null;
	private Composite parent;
	private String buttonText1;
	private String buttonText2;

	public ButtonTextButtonGroup(Composite parent, String buttonText1,
			String buttonText2) {
		this.parent = parent;
		this.buttonText1 = buttonText1;
		this.buttonText2 = buttonText2;
		initialize();
	}
	
	private void initialize() {
		button1 = new Button(parent, SWT.CHECK);
		button1.setText(buttonText1);
		text = new Text(parent, SWT.BORDER);
		
		button2 = new Button(parent, SWT.NONE);
		button2.setText(buttonText2);
	}


	/**
	 * @return the text
	 */
	public Text getText() {
		return text;
	}
	
	/**
	 * @return the parent
	 */
	public Composite getParent() {
		return parent;
	}

	/**
	 * @return the button2
	 */
	public Button getButton2() {
		return button2;
	}

	/**
	 * @return the button1
	 */
	public Button getButton1() {
		return button1;
	}
}
