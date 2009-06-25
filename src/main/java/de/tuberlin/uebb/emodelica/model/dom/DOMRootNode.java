/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.dom;

import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Class_Definition;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Class_Definition_Statement;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Stored_Definition;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Within_Statement;
import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.page.parser.symbols.Absy;

/**
 * @author choeger
 * 
 */
public class DOMRootNode extends DOMNode {

	public DOMRootNode(Model parentModel) {
		super(parentModel);
		// TODO Auto-generated constructor stub
		setValue("rootNode");
		setImageDescriptor(Images.CLASS_IMAGE_DESCRIPTOR);
		createChildren();
	}

	/**
	 * add children
	 */
	@SuppressWarnings("deprecation")
	private void createChildren() {
		if (parentModel == null)
			return;
		if (parentModel.getChild() == null)
			return;
		NT_Stored_Definition definition = (NT_Stored_Definition) parentModel
				.getChild();
		NT_Within_Statement nt_within_statement = definition
				.getNt_within_statement();

		if (nt_within_statement != null)
			addChild(new DOMPackageHead(parentModel, nt_within_statement, this));

		if (definition.getNt_class_definition_statement() != null)
		for (Absy absy : definition.getNt_class_definition_statement()
				.getList()) {
			if (absy instanceof NT_Class_Definition_Statement) {
				NT_Class_Definition_Statement statement = (NT_Class_Definition_Statement) absy;
				// NT_Final finalKW = statement.getNt_final();
				NT_Class_Definition classDef = statement
						.getNt_class_definition();

				addClassDefDOM(classDef);
			}

		}
	}

}
