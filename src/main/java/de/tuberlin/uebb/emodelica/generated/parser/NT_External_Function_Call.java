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

public class NT_External_Function_Call extends Absy {

	/* constructor */
	public NT_External_Function_Call() {
		super();
	}

	public String toString() {
		return "NT_External_Function_Call";
	}


/* attributes */
	 private NT_FunctionCallReference nt_functioncallreference = null;
	 private NT_Identifier nt_identifier = null;
	 private NT_Expression_List nt_expression_list = null;


/* getters and setters */

	public void setNt_functioncallreference(NT_FunctionCallReference nt_functioncallreference) {
		this.nt_functioncallreference = nt_functioncallreference;
	}

	public NT_FunctionCallReference getNt_functioncallreference() {
		return nt_functioncallreference;
	}

	public void setNt_identifier(NT_Identifier nt_identifier) {
		this.nt_identifier = nt_identifier;
	}

	public NT_Identifier getNt_identifier() {
		return nt_identifier;
	}

	public void setNt_expression_list(NT_Expression_List nt_expression_list) {
		this.nt_expression_list = nt_expression_list;
	}

	public NT_Expression_List getNt_expression_list() {
		return nt_expression_list;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_External_Function_Call";}

}
