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

public class NT_For_Indices extends Absy {

	/* constructor */
	public NT_For_Indices() {
		super();
	}

	public String toString() {
		return "NT_For_Indices";
	}


/* attributes */
	 private ListToken nt_for_index = null;


/* getters and setters */

	public void setNt_for_index(ListToken nt_for_index) {
		this.nt_for_index = nt_for_index;
	}

	public ListToken getNt_for_index() {
		return nt_for_index;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_For_Indices";}

}
