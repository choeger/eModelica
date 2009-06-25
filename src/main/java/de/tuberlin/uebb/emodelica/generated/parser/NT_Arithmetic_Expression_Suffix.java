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

public class NT_Arithmetic_Expression_Suffix extends Absy {

	/* constructor */
	public NT_Arithmetic_Expression_Suffix() {
		super();
	}

	public String toString() {
		return "NT_Arithmetic_Expression_Suffix";
	}


/* attributes */
	 private NT_Add_Op nt_add_op = null;
	 private NT_Term nt_term = null;


/* getters and setters */

	public void setNt_add_op(NT_Add_Op nt_add_op) {
		this.nt_add_op = nt_add_op;
	}

	public NT_Add_Op getNt_add_op() {
		return nt_add_op;
	}

	public void setNt_term(NT_Term nt_term) {
		this.nt_term = nt_term;
	}

	public NT_Term getNt_term() {
		return nt_term;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Arithmetic_Expression_Suffix";}

}
