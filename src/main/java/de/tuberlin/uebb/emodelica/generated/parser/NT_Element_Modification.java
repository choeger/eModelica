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

public class NT_Element_Modification extends Absy implements NT_ModArgument {

	/* constructor */
	public NT_Element_Modification() {
		super();
	}

	public String toString() {
		return "NT_Element_Modification";
	}


/* attributes */
	 private NT_Each nt_each = null;
	 private NT_Final nt_final = null;
	 private NT_Component_Reference nt_component_reference = null;
	 private NT_Modification nt_modification = null;
	 private NT_String_Comment nt_string_comment = null;


/* getters and setters */

	public void setNt_each(NT_Each nt_each) {
		this.nt_each = nt_each;
	}

	public NT_Each getNt_each() {
		return nt_each;
	}

	public void setNt_final(NT_Final nt_final) {
		this.nt_final = nt_final;
	}

	public NT_Final getNt_final() {
		return nt_final;
	}

	public void setNt_component_reference(NT_Component_Reference nt_component_reference) {
		this.nt_component_reference = nt_component_reference;
	}

	public NT_Component_Reference getNt_component_reference() {
		return nt_component_reference;
	}

	public void setNt_modification(NT_Modification nt_modification) {
		this.nt_modification = nt_modification;
	}

	public NT_Modification getNt_modification() {
		return nt_modification;
	}

	public void setNt_string_comment(NT_String_Comment nt_string_comment) {
		this.nt_string_comment = nt_string_comment;
	}

	public NT_String_Comment getNt_string_comment() {
		return nt_string_comment;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Element_Modification";}

}
