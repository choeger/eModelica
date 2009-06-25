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

public class NT_Connect_Clause extends Absy implements NT_EquationContent {

	/* constructor */
	public NT_Connect_Clause() {
		super();
	}

	public String toString() {
		return "NT_Connect_Clause";
	}


/* attributes */
	 private NT_Component_Reference nt_component_reference = null;
	 private NT_Component_Reference nt_component_reference1 = null;


/* getters and setters */

	public void setNt_component_reference(NT_Component_Reference nt_component_reference) {
		this.nt_component_reference = nt_component_reference;
	}

	public NT_Component_Reference getNt_component_reference() {
		return nt_component_reference;
	}

	public void setNt_component_reference1(NT_Component_Reference nt_component_reference1) {
		this.nt_component_reference1 = nt_component_reference1;
	}

	public NT_Component_Reference getNt_component_reference1() {
		return nt_component_reference1;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Connect_Clause";}

}
