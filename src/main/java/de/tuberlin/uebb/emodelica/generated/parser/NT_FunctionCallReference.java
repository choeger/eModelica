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

public class NT_FunctionCallReference extends Absy {

	/* constructor */
	public NT_FunctionCallReference() {
		super();
	}

	public String toString() {
		return "NT_FunctionCallReference";
	}


/* attributes */
	 private ListToken nt_functioncallreferencepart = null;


/* getters and setters */

	public void setNt_functioncallreferencepart(ListToken nt_functioncallreferencepart) {
		this.nt_functioncallreferencepart = nt_functioncallreferencepart;
	}

	public ListToken getNt_functioncallreferencepart() {
		return nt_functioncallreferencepart;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_FunctionCallReference";}

}
