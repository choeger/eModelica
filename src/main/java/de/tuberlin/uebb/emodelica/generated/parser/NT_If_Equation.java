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

public class NT_If_Equation extends Absy implements NT_EquationContent {

	/* constructor */
	public NT_If_Equation() {
		super();
	}

	public String toString() {
		return "NT_If_Equation";
	}


/* attributes */
	 private NT_Expression nt_expression = null;
	 private ListToken nt_equationpart = null;
	 private ListToken nt_elseifequation = null;
	 private NT_ElseEquation nt_elseequation = null;


/* getters and setters */

	public void setNt_expression(NT_Expression nt_expression) {
		this.nt_expression = nt_expression;
	}

	public NT_Expression getNt_expression() {
		return nt_expression;
	}

	public void setNt_equationpart(ListToken nt_equationpart) {
		this.nt_equationpart = nt_equationpart;
	}

	public ListToken getNt_equationpart() {
		return nt_equationpart;
	}

	public void setNt_elseifequation(ListToken nt_elseifequation) {
		this.nt_elseifequation = nt_elseifequation;
	}

	public ListToken getNt_elseifequation() {
		return nt_elseifequation;
	}

	public void setNt_elseequation(NT_ElseEquation nt_elseequation) {
		this.nt_elseequation = nt_elseequation;
	}

	public NT_ElseEquation getNt_elseequation() {
		return nt_elseequation;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_If_Equation";}

}
