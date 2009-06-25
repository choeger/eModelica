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

public class NT_ModArgumentList extends Absy {

	/* constructor */
	public NT_ModArgumentList() {
		super();
	}

	public String toString() {
		return "NT_ModArgumentList";
	}


/* attributes */
	 private ListToken nt_modargument = null;


/* getters and setters */

	public void setNt_modargument(ListToken nt_modargument) {
		this.nt_modargument = nt_modargument;
	}

	public ListToken getNt_modargument() {
		return nt_modargument;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_ModArgumentList";}

}
