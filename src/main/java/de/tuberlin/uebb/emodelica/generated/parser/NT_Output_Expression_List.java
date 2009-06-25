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

public class NT_Output_Expression_List extends Absy {

	/* constructor */
	public NT_Output_Expression_List() {
		super();
	}

	public String toString() {
		return "NT_Output_Expression_List";
	}


/* attributes */
	 private NT_Expression nt_expression = null;
	 private ListToken nt_output_expression_list_suffix = null;


/* getters and setters */

	public void setNt_expression(NT_Expression nt_expression) {
		this.nt_expression = nt_expression;
	}

	public NT_Expression getNt_expression() {
		return nt_expression;
	}

	public void setNt_output_expression_list_suffix(ListToken nt_output_expression_list_suffix) {
		this.nt_output_expression_list_suffix = nt_output_expression_list_suffix;
	}

	public ListToken getNt_output_expression_list_suffix() {
		return nt_output_expression_list_suffix;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Output_Expression_List";}

}
