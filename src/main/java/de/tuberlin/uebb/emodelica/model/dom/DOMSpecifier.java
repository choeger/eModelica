/**
 * 
 */
package de.tuberlin.uebb.emodelica.model.dom;

import java.util.List;

import de.tuberlin.uebb.emodelica.generated.parser.NT_ClassDefinitionOrMultipleComponentClause;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Class_Definition;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Component;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Comp_Spec;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Component_Declaration;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Component_Part;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Declaration;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Der_Spec;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Element;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Element_Part;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Import_Clause;
import de.tuberlin.uebb.emodelica.generated.parser.NT_MultipleComponentClause;
import de.tuberlin.uebb.emodelica.generated.parser.NT_Specifier;
import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.page.parser.symbols.Absy;
import de.tuberlin.uebb.page.parser.symbols.ListToken;

/**
 * @author choeger
 *
 */
public abstract class DOMSpecifier extends DOMNode {

	protected NT_Specifier specifier;
	
	public DOMSpecifier(Model parentModel, NT_Specifier specifier, DOMNode parent) {
		super(parentModel);
		setParent(parent);
		this.specifier = specifier;
		this.absy = (Absy) specifier;
		
		if (specifier instanceof NT_Comp_Spec) {
			setValue(((NT_Comp_Spec)specifier).getNt_identifier().getValue().getValue());
		}
		if (specifier instanceof NT_Der_Spec) {
			setValue(((NT_Der_Spec)specifier).getNt_identifier().getValue().getValue());
		}
		
		createChildren();
	}
	
	private void createChildren() {
		
		if (this.specifier instanceof NT_Comp_Spec) {
			NT_Comp_Spec composition = (NT_Comp_Spec)specifier;
			
			ListToken nt_element_ = composition.getNt_composition().getNt_element_();
			if (nt_element_ != null)
			for (Absy element : nt_element_) {
				if (element instanceof NT_Element_Part)
					addPrivateElement((NT_Element_Part) element);
			}
		}
	}
	
	private void addPrivateElement(NT_Element_Part elementPart) {
		NT_Element element = elementPart.getNt_element();
		
		if (element instanceof NT_Import_Clause) {
			addImportClause(new DOMSingleImport(parentModel, (NT_Import_Clause)element, this), elementPart);
		}
		//TODO: extends clause
		if (element instanceof NT_Component_Part) {
			//TODO: inner, outer, redeclare decorations
			NT_Component component = ((NT_Component_Part)element).getNt_component();
			NT_ClassDefinitionOrMultipleComponentClause classDefOrComponent = component.getNt_classdefinitionormultiplecomponentclause();
			if (classDefOrComponent instanceof NT_Class_Definition) {
				NT_Class_Definition def = (NT_Class_Definition)classDefOrComponent;
				addClassDefDOM(def);
			} else {
				NT_MultipleComponentClause componentClause = (NT_MultipleComponentClause)classDefOrComponent;
				for (Absy component_declaration :componentClause.getNt_component_list().getNt_component_declaration()) {
					assert(component_declaration instanceof NT_Component_Declaration) : "All members of Nt_component_declaration must be of type NT_Component_Declaration";
					NT_Declaration declaration = ((NT_Component_Declaration)component_declaration).getNt_declaration();
					addChild(new DOMPrivateElement(parentModel, componentClause.getNt_type_prefix(), declaration, this));
				}
			}
		}
		
	}

	private void addImportClause(DOMSingleImport singleImport, NT_Element_Part elementPart) {
		//singleton import container ahead of all other stuff
		List<DOMNode> children = getChildren();
		int last = children.size() - 1;
		
		if (last < 0 || !(children.get(last) instanceof DOMImports)) {
			children.add(new DOMImports(parentModel, this));
			last++;
		}
		DOMImports imports = (DOMImports)children.get(last);
		imports.updateFoldable(elementPart);
		imports.addChild(singleImport);
		singleImport.setParent(imports);
	}
	
}
