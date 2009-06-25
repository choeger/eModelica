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

public class NT_Arithmetic_Expression extends Absy {

	/* constructor */
	public NT_Arithmetic_Expression() {
		super();
	}

	public String toString() {
		return "NT_Arithmetic_Expression";
	}


/* attributes */
	 private NT_Add_Op nt_add_op = null;
	 private NT_Term nt_term = null;
	 private ListToken nt_arithmetic_expression_suffix = null;


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

	public void setNt_arithmetic_expression_suffix(ListToken nt_arithmetic_expression_suffix) {
		this.nt_arithmetic_expression_suffix = nt_arithmetic_expression_suffix;
	}

	public ListToken getNt_arithmetic_expression_suffix() {
		return nt_arithmetic_expression_suffix;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Arithmetic_Expression";}

}
