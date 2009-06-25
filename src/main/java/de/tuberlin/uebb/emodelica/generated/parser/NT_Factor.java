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

public class NT_Factor extends Absy {

	/* constructor */
	public NT_Factor() {
		super();
	}

	public String toString() {
		return "NT_Factor";
	}


/* attributes */
	 private NT_Primary nt_primary = null;
	 private NT_FactorSuffix nt_factorsuffix = null;


/* getters and setters */

	public void setNt_primary(NT_Primary nt_primary) {
		this.nt_primary = nt_primary;
	}

	public NT_Primary getNt_primary() {
		return nt_primary;
	}

	public void setNt_factorsuffix(NT_FactorSuffix nt_factorsuffix) {
		this.nt_factorsuffix = nt_factorsuffix;
	}

	public NT_FactorSuffix getNt_factorsuffix() {
		return nt_factorsuffix;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Factor";}

}
