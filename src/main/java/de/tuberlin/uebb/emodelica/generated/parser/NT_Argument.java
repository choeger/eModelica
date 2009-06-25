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

public class NT_Argument extends Absy {

	/* constructor */
	public NT_Argument() {
		super();
	}

	public String toString() {
		return "NT_Argument";
	}


/* attributes */
	 private NT_ArgumentName nt_argumentname = null;
	 private NT_Expression nt_expression = null;


/* getters and setters */

	public void setNt_argumentname(NT_ArgumentName nt_argumentname) {
		this.nt_argumentname = nt_argumentname;
	}

	public NT_ArgumentName getNt_argumentname() {
		return nt_argumentname;
	}

	public void setNt_expression(NT_Expression nt_expression) {
		this.nt_expression = nt_expression;
	}

	public NT_Expression getNt_expression() {
		return nt_expression;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Argument";}

}
