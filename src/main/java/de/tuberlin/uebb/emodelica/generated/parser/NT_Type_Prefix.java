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

public class NT_Type_Prefix extends Absy {

	/* constructor */
	public NT_Type_Prefix() {
		super();
	}

	public String toString() {
		return "NT_Type_Prefix";
	}


/* attributes */
	 private NT_Flow nt_flow = null;
	 private NT_Kind nt_kind = null;
	 private NT_InputOrOutput nt_inputoroutput = null;


/* getters and setters */

	public void setNt_flow(NT_Flow nt_flow) {
		this.nt_flow = nt_flow;
	}

	public NT_Flow getNt_flow() {
		return nt_flow;
	}

	public void setNt_kind(NT_Kind nt_kind) {
		this.nt_kind = nt_kind;
	}

	public NT_Kind getNt_kind() {
		return nt_kind;
	}

	public void setNt_inputoroutput(NT_InputOrOutput nt_inputoroutput) {
		this.nt_inputoroutput = nt_inputoroutput;
	}

	public NT_InputOrOutput getNt_inputoroutput() {
		return nt_inputoroutput;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Type_Prefix";}

}
