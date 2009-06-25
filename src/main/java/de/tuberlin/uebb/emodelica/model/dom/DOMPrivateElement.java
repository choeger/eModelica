/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.dom;

import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Declaration;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Type_Prefix;
import de.tuberlin.uebb.emodelica.model.Model;

/**
 * @author choeger
 *
 */
public class DOMPrivateElement extends DOMNode {

	public DOMPrivateElement(Model parentModel, NT_Type_Prefix nt_type_prefix,
			NT_Declaration declaration, DOMSpecifier specifier) {
		super(parentModel);
		absy = declaration.getNt_identifier();
		setParent(specifier);
		setValue(declaration.getNt_identifier().getValue().getValue());
		//TODO: select icon based on 	nt_type_prefix
		setImageDescriptor(Images.PRIVATE_FIELD_DESCRIPTOR);
	}
}
