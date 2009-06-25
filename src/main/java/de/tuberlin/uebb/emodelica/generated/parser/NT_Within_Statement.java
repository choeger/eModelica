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

public class NT_Within_Statement extends Absy {

	/* constructor */
	public NT_Within_Statement() {
		super();
	}

	public String toString() {
		return "NT_Within_Statement";
	}


/* attributes */
	 private NT_Name nt_name = null;


/* getters and setters */

	public void setNt_name(NT_Name nt_name) {
		this.nt_name = nt_name;
	}

	public NT_Name getNt_name() {
		return nt_name;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Within_Statement";}

}
