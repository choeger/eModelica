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

public class NT_ArrayConstructPrimary extends Absy implements NT_Primary {

	/* constructor */
	public NT_ArrayConstructPrimary() {
		super();
	}

	public String toString() {
		return "NT_ArrayConstructPrimary";
	}


/* attributes */
	 private NT_FunctionArgumentList nt_functionargumentlist = null;


/* getters and setters */

	public void setNt_functionargumentlist(NT_FunctionArgumentList nt_functionargumentlist) {
		this.nt_functionargumentlist = nt_functionargumentlist;
	}

	public NT_FunctionArgumentList getNt_functionargumentlist() {
		return nt_functionargumentlist;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_ArrayConstructPrimary";}

}
