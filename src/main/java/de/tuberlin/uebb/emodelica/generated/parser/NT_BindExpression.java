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

public class NT_BindExpression extends Absy implements NT_EquationContent {

	/* constructor */
	public NT_BindExpression() {
		super();
	}

	public String toString() {
		return "NT_BindExpression";
	}


/* attributes */
	 private NT_Simple_Expression nt_simple_expression = null;
	 private NT_EquationRhs nt_equationrhs = null;


/* getters and setters */

	public void setNt_simple_expression(NT_Simple_Expression nt_simple_expression) {
		this.nt_simple_expression = nt_simple_expression;
	}

	public NT_Simple_Expression getNt_simple_expression() {
		return nt_simple_expression;
	}

	public void setNt_equationrhs(NT_EquationRhs nt_equationrhs) {
		this.nt_equationrhs = nt_equationrhs;
	}

	public NT_EquationRhs getNt_equationrhs() {
		return nt_equationrhs;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_BindExpression";}

}
