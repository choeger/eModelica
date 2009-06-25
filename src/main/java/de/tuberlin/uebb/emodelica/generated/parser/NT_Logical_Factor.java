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

public class NT_Logical_Factor extends Absy {

	/* constructor */
	public NT_Logical_Factor() {
		super();
	}

	public String toString() {
		return "NT_Logical_Factor";
	}


/* attributes */
	 private NT_Negation nt_negation = null;
	 private NT_Relation nt_relation = null;


/* getters and setters */

	public void setNt_negation(NT_Negation nt_negation) {
		this.nt_negation = nt_negation;
	}

	public NT_Negation getNt_negation() {
		return nt_negation;
	}

	public void setNt_relation(NT_Relation nt_relation) {
		this.nt_relation = nt_relation;
	}

	public NT_Relation getNt_relation() {
		return nt_relation;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Logical_Factor";}

}
