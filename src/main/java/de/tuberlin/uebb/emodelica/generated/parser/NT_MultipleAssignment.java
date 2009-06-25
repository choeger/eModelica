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

public class NT_MultipleAssignment extends Absy implements NT_StatementContent {

	/* constructor */
	public NT_MultipleAssignment() {
		super();
	}

	public String toString() {
		return "NT_MultipleAssignment";
	}


/* attributes */
	 private NT_Output_Expression_List nt_output_expression_list = null;
	 private NT_Component_Reference nt_component_reference = null;
	 private NT_Function_Call_Args nt_function_call_args = null;


/* getters and setters */

	public void setNt_output_expression_list(NT_Output_Expression_List nt_output_expression_list) {
		this.nt_output_expression_list = nt_output_expression_list;
	}

	public NT_Output_Expression_List getNt_output_expression_list() {
		return nt_output_expression_list;
	}

	public void setNt_component_reference(NT_Component_Reference nt_component_reference) {
		this.nt_component_reference = nt_component_reference;
	}

	public NT_Component_Reference getNt_component_reference() {
		return nt_component_reference;
	}

	public void setNt_function_call_args(NT_Function_Call_Args nt_function_call_args) {
		this.nt_function_call_args = nt_function_call_args;
	}

	public NT_Function_Call_Args getNt_function_call_args() {
		return nt_function_call_args;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_MultipleAssignment";}

}
