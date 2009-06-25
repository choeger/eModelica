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

public class NT_RelationSuffix extends Absy {

	/* constructor */
	public NT_RelationSuffix() {
		super();
	}

	public String toString() {
		return "NT_RelationSuffix";
	}


/* attributes */
	 private NT_Rel_Op nt_rel_op = null;
	 private NT_Arithmetic_Expression nt_arithmetic_expression = null;


/* getters and setters */

	public void setNt_rel_op(NT_Rel_Op nt_rel_op) {
		this.nt_rel_op = nt_rel_op;
	}

	public NT_Rel_Op getNt_rel_op() {
		return nt_rel_op;
	}

	public void setNt_arithmetic_expression(NT_Arithmetic_Expression nt_arithmetic_expression) {
		this.nt_arithmetic_expression = nt_arithmetic_expression;
	}

	public NT_Arithmetic_Expression getNt_arithmetic_expression() {
		return nt_arithmetic_expression;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_RelationSuffix";}

}
