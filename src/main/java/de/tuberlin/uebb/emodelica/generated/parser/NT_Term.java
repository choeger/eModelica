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

public class NT_Term extends Absy {

	/* constructor */
	public NT_Term() {
		super();
	}

	public String toString() {
		return "NT_Term";
	}


/* attributes */
	 private NT_Factor nt_factor = null;
	 private ListToken nt_termsuffix = null;


/* getters and setters */

	public void setNt_factor(NT_Factor nt_factor) {
		this.nt_factor = nt_factor;
	}

	public NT_Factor getNt_factor() {
		return nt_factor;
	}

	public void setNt_termsuffix(ListToken nt_termsuffix) {
		this.nt_termsuffix = nt_termsuffix;
	}

	public ListToken getNt_termsuffix() {
		return nt_termsuffix;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Term";}

}
