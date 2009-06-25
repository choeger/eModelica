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

public class NT_ElseWhenStatement extends Absy {

	/* constructor */
	public NT_ElseWhenStatement() {
		super();
	}

	public String toString() {
		return "NT_ElseWhenStatement";
	}


/* attributes */
	 private NT_Expression nt_expression = null;
	 private ListToken nt_statementpart = null;


/* getters and setters */

	public void setNt_expression(NT_Expression nt_expression) {
		this.nt_expression = nt_expression;
	}

	public NT_Expression getNt_expression() {
		return nt_expression;
	}

	public void setNt_statementpart(ListToken nt_statementpart) {
		this.nt_statementpart = nt_statementpart;
	}

	public ListToken getNt_statementpart() {
		return nt_statementpart;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_ElseWhenStatement";}

}
