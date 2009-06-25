/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.dom;

import org.eclipse.jface.text.Position;

import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Element_Part;
import de.tuberlin.uebb.emodelica.model.Model;

/**
 * @author choeger
 *
 */
public class DOMImports extends DOMNode {
	
	private Position foldable;
	
	public DOMImports(Model parentModel, DOMSpecifier parent) {
		super(parentModel);
		setParent(parent);
		setValue("Imports");
		setImageDescriptor(Images.IMPORT_IMAGE_DESCRIPTOR);
	}

	public void updateFoldable(NT_Element_Part elementPart) {
		Position pos = parentModel.rangeToFoldablePosition(elementPart.getRange());
		if (foldable == null) {
			foldable = pos;
			parentModel.getAllFoldablePositions().add(foldable);
		} else
			foldable.length = pos.offset + pos.length - foldable.offset;
	}
	
}
