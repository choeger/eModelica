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

public class NT_If_Expression extends Absy implements NT_Expression {

	/* constructor */
	public NT_If_Expression() {
		super();
	}

	public String toString() {
		return "NT_If_Expression";
	}


/* attributes */
	 private NT_Expression nt_expression = null;
	 private NT_Expression nt_expression1 = null;
	 private ListToken nt_elseifexpression = null;
	 private NT_Expression nt_expression2 = null;


/* getters and setters */

	public void setNt_expression(NT_Expression nt_expression) {
		this.nt_expression = nt_expression;
	}

	public NT_Expression getNt_expression() {
		return nt_expression;
	}

	public void setNt_expression1(NT_Expression nt_expression1) {
		this.nt_expression1 = nt_expression1;
	}

	public NT_Expression getNt_expression1() {
		return nt_expression1;
	}

	public void setNt_elseifexpression(ListToken nt_elseifexpression) {
		this.nt_elseifexpression = nt_elseifexpression;
	}

	public ListToken getNt_elseifexpression() {
		return nt_elseifexpression;
	}

	public void setNt_expression2(NT_Expression nt_expression2) {
		this.nt_expression2 = nt_expression2;
	}

	public NT_Expression getNt_expression2() {
		return nt_expression2;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_If_Expression";}

}
