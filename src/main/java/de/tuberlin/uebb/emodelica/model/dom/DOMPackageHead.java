/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.dom;

import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Within_Statement;
import de.tuberlin.uebb.emodelica.model.Model;

/**
 * @author choeger
 *
 */
public class DOMPackageHead extends DOMNode {
	private NT_Within_Statement withinStatement;
	
	public DOMPackageHead(Model parentModel, NT_Within_Statement withinStatement, DOMNode parent) {
		super(parentModel);
		
		setWithinStatement(withinStatement);
		this.absy = withinStatement;
		if (withinStatement.getNt_name() != null)		
			setValue(ListTokenToString(withinStatement.getNt_name().getNt_identifier(), "."));
		else setValue("default package");
		
		setImageDescriptor(Images.WITHIN_PKG_IMAGE_DESCRIPTOR);
		setParent(parent);
	}

	/**
	 * @param withinStatement the withinStatement to set
	 */
	public void setWithinStatement(NT_Within_Statement withinStatement) {
		this.withinStatement = withinStatement;
	}

	/**
	 * @return the withinStatement
	 */
	public NT_Within_Statement getWithinStatement() {
		return withinStatement;
	}
}
