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

public class NT_MultipleComponentClause extends Absy implements NT_ClassDefinitionOrMultipleComponentClause {

	/* constructor */
	public NT_MultipleComponentClause() {
		super();
	}

	public String toString() {
		return "NT_MultipleComponentClause";
	}


/* attributes */
	 private NT_Type_Prefix nt_type_prefix = null;
	 private NT_Name nt_name = null;
	 private NT_Array_Subscripts nt_array_subscripts = null;
	 private NT_Component_List nt_component_list = null;


/* getters and setters */

	public void setNt_type_prefix(NT_Type_Prefix nt_type_prefix) {
		this.nt_type_prefix = nt_type_prefix;
	}

	public NT_Type_Prefix getNt_type_prefix() {
		return nt_type_prefix;
	}

	public void setNt_name(NT_Name nt_name) {
		this.nt_name = nt_name;
	}

	public NT_Name getNt_name() {
		return nt_name;
	}

	public void setNt_array_subscripts(NT_Array_Subscripts nt_array_subscripts) {
		this.nt_array_subscripts = nt_array_subscripts;
	}

	public NT_Array_Subscripts getNt_array_subscripts() {
		return nt_array_subscripts;
	}

	public void setNt_component_list(NT_Component_List nt_component_list) {
		this.nt_component_list = nt_component_list;
	}

	public NT_Component_List getNt_component_list() {
		return nt_component_list;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_MultipleComponentClause";}

}
