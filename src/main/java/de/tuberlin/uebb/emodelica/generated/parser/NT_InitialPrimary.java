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

public class NT_InitialPrimary extends Absy implements NT_Primary {

	/* constructor */
	public NT_InitialPrimary() {
		super();
	}

	public String toString() {
		return "NT_InitialPrimary";
	}


/* attributes */
	 private NT_Function_Call_Args nt_function_call_args = null;


/* getters and setters */

	public void setNt_function_call_args(NT_Function_Call_Args nt_function_call_args) {
		this.nt_function_call_args = nt_function_call_args;
	}

	public NT_Function_Call_Args getNt_function_call_args() {
		return nt_function_call_args;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_InitialPrimary";}

}
