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

public class NT_Enum_List extends Absy implements NT_Enum_Spec_Part {

	/* constructor */
	public NT_Enum_List() {
		super();
	}

	public String toString() {
		return "NT_Enum_List";
	}


/* attributes */
	 private ListToken nt_enumeration_literal = null;


/* getters and setters */

	public void setNt_enumeration_literal(ListToken nt_enumeration_literal) {
		this.nt_enumeration_literal = nt_enumeration_literal;
	}

	public ListToken getNt_enumeration_literal() {
		return nt_enumeration_literal;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Enum_List";}

}
