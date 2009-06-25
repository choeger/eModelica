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

public class NT_Expression_List extends Absy {

	/* constructor */
	public NT_Expression_List() {
		super();
	}

	public String toString() {
		return "NT_Expression_List";
	}


/* attributes */
	 private ListToken nt_expression = null;


/* getters and setters */

	public void setNt_expression(ListToken nt_expression) {
		this.nt_expression = nt_expression;
	}

	public ListToken getNt_expression() {
		return nt_expression;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Expression_List";}

}
