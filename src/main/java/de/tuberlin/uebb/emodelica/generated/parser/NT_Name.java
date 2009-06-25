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

public class NT_Name extends Absy {

	/* constructor */
	public NT_Name() {
		super();
	}

	public String toString() {
		return "NT_Name";
	}


/* attributes */
	 private ListToken nt_identifier = null;


/* getters and setters */

	public void setNt_identifier(ListToken nt_identifier) {
		this.nt_identifier = nt_identifier;
	}

	public ListToken getNt_identifier() {
		return nt_identifier;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Name";}

}
