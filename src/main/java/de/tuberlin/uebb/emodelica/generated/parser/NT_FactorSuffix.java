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

public class NT_FactorSuffix extends Absy {

	/* constructor */
	public NT_FactorSuffix() {
		super();
	}

	public String toString() {
		return "NT_FactorSuffix";
	}


/* attributes */
	 private NT_Factor_Op nt_factor_op = null;
	 private NT_Primary nt_primary = null;


/* getters and setters */

	public void setNt_factor_op(NT_Factor_Op nt_factor_op) {
		this.nt_factor_op = nt_factor_op;
	}

	public NT_Factor_Op getNt_factor_op() {
		return nt_factor_op;
	}

	public void setNt_primary(NT_Primary nt_primary) {
		this.nt_primary = nt_primary;
	}

	public NT_Primary getNt_primary() {
		return nt_primary;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_FactorSuffix";}

}
