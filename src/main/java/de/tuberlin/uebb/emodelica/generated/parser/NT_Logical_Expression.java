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

public class NT_Logical_Expression extends Absy {

	/* constructor */
	public NT_Logical_Expression() {
		super();
	}

	public String toString() {
		return "NT_Logical_Expression";
	}


/* attributes */
	 private ListToken nt_logical_term = null;


/* getters and setters */

	public void setNt_logical_term(ListToken nt_logical_term) {
		this.nt_logical_term = nt_logical_term;
	}

	public ListToken getNt_logical_term() {
		return nt_logical_term;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Logical_Expression";}

}
