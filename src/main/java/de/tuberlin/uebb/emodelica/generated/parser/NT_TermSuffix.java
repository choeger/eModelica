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

public class NT_TermSuffix extends Absy {

	/* constructor */
	public NT_TermSuffix() {
		super();
	}

	public String toString() {
		return "NT_TermSuffix";
	}


/* attributes */
	 private NT_Mul_Op nt_mul_op = null;
	 private NT_Factor nt_factor = null;


/* getters and setters */

	public void setNt_mul_op(NT_Mul_Op nt_mul_op) {
		this.nt_mul_op = nt_mul_op;
	}

	public NT_Mul_Op getNt_mul_op() {
		return nt_mul_op;
	}

	public void setNt_factor(NT_Factor nt_factor) {
		this.nt_factor = nt_factor;
	}

	public NT_Factor getNt_factor() {
		return nt_factor;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_TermSuffix";}

}
