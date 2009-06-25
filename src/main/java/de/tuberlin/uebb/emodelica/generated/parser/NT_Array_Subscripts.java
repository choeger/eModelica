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

public class NT_Array_Subscripts extends Absy {

	/* constructor */
	public NT_Array_Subscripts() {
		super();
	}

	public String toString() {
		return "NT_Array_Subscripts";
	}


/* attributes */
	 private ListToken nt_subscript = null;


/* getters and setters */

	public void setNt_subscript(ListToken nt_subscript) {
		this.nt_subscript = nt_subscript;
	}

	public ListToken getNt_subscript() {
		return nt_subscript;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Array_Subscripts";}

}
