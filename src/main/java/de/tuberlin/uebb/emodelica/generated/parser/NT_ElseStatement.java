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

public class NT_ElseStatement extends Absy {

	/* constructor */
	public NT_ElseStatement() {
		super();
	}

	public String toString() {
		return "NT_ElseStatement";
	}


/* attributes */
	 private ListToken nt_statementpart = null;


/* getters and setters */

	public void setNt_statementpart(ListToken nt_statementpart) {
		this.nt_statementpart = nt_statementpart;
	}

	public ListToken getNt_statementpart() {
		return nt_statementpart;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_ElseStatement";}

}
