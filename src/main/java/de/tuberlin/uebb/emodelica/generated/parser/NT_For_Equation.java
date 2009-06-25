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

public class NT_For_Equation extends Absy implements NT_EquationContent {

	/* constructor */
	public NT_For_Equation() {
		super();
	}

	public String toString() {
		return "NT_For_Equation";
	}


/* attributes */
	 private NT_For_Indices nt_for_indices = null;
	 private ListToken nt_equationpart = null;


/* getters and setters */

	public void setNt_for_indices(NT_For_Indices nt_for_indices) {
		this.nt_for_indices = nt_for_indices;
	}

	public NT_For_Indices getNt_for_indices() {
		return nt_for_indices;
	}

	public void setNt_equationpart(ListToken nt_equationpart) {
		this.nt_equationpart = nt_equationpart;
	}

	public ListToken getNt_equationpart() {
		return nt_equationpart;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_For_Equation";}

}
