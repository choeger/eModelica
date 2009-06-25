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

public class NT_Component_List extends Absy {

	/* constructor */
	public NT_Component_List() {
		super();
	}

	public String toString() {
		return "NT_Component_List";
	}


/* attributes */
	 private ListToken nt_component_declaration = null;


/* getters and setters */

	public void setNt_component_declaration(ListToken nt_component_declaration) {
		this.nt_component_declaration = nt_component_declaration;
	}

	public ListToken getNt_component_declaration() {
		return nt_component_declaration;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Component_List";}

}
