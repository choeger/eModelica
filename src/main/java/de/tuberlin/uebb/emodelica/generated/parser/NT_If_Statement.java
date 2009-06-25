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

public class NT_If_Statement extends Absy implements NT_StatementContent {

	/* constructor */
	public NT_If_Statement() {
		super();
	}

	public String toString() {
		return "NT_If_Statement";
	}


/* attributes */
	 private NT_Expression nt_expression = null;
	 private ListToken nt_statementpart = null;
	 private ListToken nt_elseifstatement = null;
	 private NT_ElseStatement nt_elsestatement = null;


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

	public void setNt_elseifstatement(ListToken nt_elseifstatement) {
		this.nt_elseifstatement = nt_elseifstatement;
	}

	public ListToken getNt_elseifstatement() {
		return nt_elseifstatement;
	}

	public void setNt_elsestatement(NT_ElseStatement nt_elsestatement) {
		this.nt_elsestatement = nt_elsestatement;
	}

	public NT_ElseStatement getNt_elsestatement() {
		return nt_elsestatement;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_If_Statement";}

}
