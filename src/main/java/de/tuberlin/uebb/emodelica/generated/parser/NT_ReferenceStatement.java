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

public class NT_ReferenceStatement extends Absy implements NT_StatementContent {

	/* constructor */
	public NT_ReferenceStatement() {
		super();
	}

	public String toString() {
		return "NT_ReferenceStatement";
	}


/* attributes */
	 private NT_Component_Reference nt_component_reference = null;
	 private NT_ReferenceRhs nt_referencerhs = null;


/* getters and setters */

	public void setNt_component_reference(NT_Component_Reference nt_component_reference) {
		this.nt_component_reference = nt_component_reference;
	}

	public NT_Component_Reference getNt_component_reference() {
		return nt_component_reference;
	}

	public void setNt_referencerhs(NT_ReferenceRhs nt_referencerhs) {
		this.nt_referencerhs = nt_referencerhs;
	}

	public NT_ReferenceRhs getNt_referencerhs() {
		return nt_referencerhs;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_ReferenceStatement";}

}
