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

public class NT_String_Comment extends Absy {

	/* constructor */
	public NT_String_Comment() {
		super();
	}

	public String toString() {
		return "NT_String_Comment";
	}


/* attributes */
	 private ListToken nt_string = null;


/* getters and setters */

	public void setNt_string(ListToken nt_string) {
		this.nt_string = nt_string;
	}

	public ListToken getNt_string() {
		return nt_string;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_String_Comment";}

}
