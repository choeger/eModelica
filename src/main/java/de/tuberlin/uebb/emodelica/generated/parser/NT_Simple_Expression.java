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

public class NT_Simple_Expression extends Absy implements NT_Expression {

	/* constructor */
	public NT_Simple_Expression() {
		super();
	}

	public String toString() {
		return "NT_Simple_Expression";
	}


/* attributes */
	 private ListToken nt_logical_expression = null;


/* getters and setters */

	public void setNt_logical_expression(ListToken nt_logical_expression) {
		this.nt_logical_expression = nt_logical_expression;
	}

	public ListToken getNt_logical_expression() {
		return nt_logical_expression;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Simple_Expression";}

}
