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

public class NT_EquationPart extends Absy implements NT_EquationSectionElement {

	/* constructor */
	public NT_EquationPart() {
		super();
	}

	public String toString() {
		return "NT_EquationPart";
	}


/* attributes */
	 private NT_Equation nt_equation = null;


/* getters and setters */

	public void setNt_equation(NT_Equation nt_equation) {
		this.nt_equation = nt_equation;
	}

	public NT_Equation getNt_equation() {
		return nt_equation;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_EquationPart";}

}
