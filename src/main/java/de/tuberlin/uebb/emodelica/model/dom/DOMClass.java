package de.tuberlin.uebb.emodelica.model.dom;

import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Specifier;
import de.tuberlin.uebb.emodelica.model.Model;

public class DOMClass extends DOMSpecifier {

	public DOMClass(Model parentModel, NT_Specifier specifier, DOMNode parent) {
		super(parentModel, specifier, parent);
		setImageDescriptor(Images.CLASS_IMAGE_DESCRIPTOR);
	}

}
