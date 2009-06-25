/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.dom;

import de.tuberlin.uebb.emodelica.Images;
import de.tuberlin.uebb.emodelica.generated.parser.NT_AllElementExt;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Import_Clause;
import de.tuberlin.uebb.emodelica.generated.parser.NT_NamedImport;
import de.tuberlin.uebb.emodelica.generated.parser.NT_RenamingImport;
import de.tuberlin.uebb.emodelica.model.Model;

/**
 * @author choeger
 *
 */
public class DOMSingleImport extends DOMNode {

	public DOMSingleImport(Model parentModel, NT_Import_Clause importClause, DOMNode parent) {
		super(parentModel);
		setParent(parent);
		
		if (importClause instanceof NT_NamedImport) {
			NT_NamedImport namedImport = (NT_NamedImport)importClause;
			absy = namedImport;
			String value = ListTokenToString(namedImport.getNt_name().getNt_identifier(),".");
			NT_AllElementExt nt_allelementext = namedImport.getNt_allelementext();
			if (nt_allelementext != null)
				value+=".*";
			setValue(value);
		} else {
			NT_RenamingImport renamingImport = (NT_RenamingImport)importClause;
			absy = renamingImport;
			setValue(ListTokenToString(renamingImport.getNt_name().getNt_identifier(),"."));
		}
		setImageDescriptor(Images.SINGLE_IMPORT_IMAGE_DESCRIPTOR);
	}
	
}
