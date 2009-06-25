/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.dom;

import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Comp_Spec;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Der_Spec;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Specifier;
import de.tuberlin.uebb.emodelica.model.Model;

/**
 * @author choeger
 *
 */
public class DOMPackage extends DOMSpecifier {

	public DOMPackage(Model parentModel, NT_Specifier specifier, DOMNode parent) {
		super(parentModel,specifier,parent);
		setImageDescriptor(Images.PKG_IMAGE_DESCRIPTOR);
	}

}
