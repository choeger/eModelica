/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui;

import org.eclipse.jface.viewers.CellEditor.LayoutData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author choeger
 * 
 */
public class LabelTextButtonGroup {

	private Label label = null;
	private Text text = null;
	private Button button = null;
	private Composite parent;
	private String labelText;
	private String buttonText;
	private IWidgetDelegate delegate;

	public LabelTextButtonGroup(Composite parent, String labelText,
			String buttonText, IWidgetDelegate delegate) {
		this.parent = parent;
		this.delegate = delegate;
		this.labelText = labelText;
		this.buttonText = buttonText;
		initialize();
	}
	
	private void initialize() {
		label = new Label(parent, SWT.NONE);
		label.setText(labelText);
		text = new Text(parent, SWT.BORDER);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		button = delegate.createButton(parent, buttonText);
		button.setText(buttonText);
		
		button.pack();
		label.pack();
		text.pack();
	}

	/**
	 * @return the label
	 */
	public Label getLabel() {
		return label;
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
