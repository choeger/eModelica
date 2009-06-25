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

public class NT_Logical_Term extends Absy {

	/* constructor */
	public NT_Logical_Term() {
		super();
	}

	public String toString() {
		return "NT_Logical_Term";
	}


/* attributes */
	 private ListToken nt_logical_factor = null;


/* getters and setters */

	public void setNt_logical_factor(ListToken nt_logical_factor) {
		this.nt_logical_factor = nt_logical_factor;
	}

	public ListToken getNt_logical_factor() {
		return nt_logical_factor;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Logical_Term";}

}
