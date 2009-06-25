/*
This class was automagically created by the parser generator.
DO NOT EDIT it!
changes are likely to get lost
*/
package de.tuberlin.uebb.emodelica.generated.parser;

import java.util.ArrayList;
import de.tuberlin.uebb.page.parser.symbols.Absy;
import de.tuberlin.uebb.page.parser.symbols.ListToken;
import de.tuberlin.uebb.page.grammar.symbols.Terminal;
@SuppressWarnings("unused")

public class NT_Composition extends Absy {

	/* constructor */
	public NT_Composition() {
		super();
	}

	public String toString() {
		return "NT_Composition";
	}


/* attributes */
	 private ListToken nt_element_ = null;
	 private ListToken nt_composition_element = null;
	 private NT_External_Comp_Element nt_external_comp_element = null;


/* getters and setters */

	public void setNt_element_(ListToken nt_element_) {
		this.nt_element_ = nt_element_;
	}

	public ListToken getNt_element_() {
		return nt_element_;
	}

	public void setNt_composition_element(ListToken nt_composition_element) {
		this.nt_composition_element = nt_composition_element;
	}

	public ListToken getNt_composition_element() {
		return nt_composition_element;
	}

	public void setNt_external_comp_element(NT_External_Comp_Element nt_external_comp_element) {
		this.nt_external_comp_element = nt_external_comp_element;
	}

	public NT_External_Comp_Element getNt_external_comp_element() {
		return nt_external_comp_element;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Composition";}

}
