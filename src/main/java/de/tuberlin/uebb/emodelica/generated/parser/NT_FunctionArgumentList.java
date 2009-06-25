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

public class NT_FunctionArgumentList extends Absy {

	/* constructor */
	public NT_FunctionArgumentList() {
		super();
	}

	public String toString() {
		return "NT_FunctionArgumentList";
	}


/* attributes */
	 private ListToken nt_argument = null;
	 private NT_ForSuffix nt_forsuffix = null;


/* getters and setters */

	public void setNt_argument(ListToken nt_argument) {
		this.nt_argument = nt_argument;
	}

	public ListToken getNt_argument() {
		return nt_argument;
	}

	public void setNt_forsuffix(NT_ForSuffix nt_forsuffix) {
		this.nt_forsuffix = nt_forsuffix;
	}

	public NT_ForSuffix getNt_forsuffix() {
		return nt_forsuffix;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_FunctionArgumentList";}

}
