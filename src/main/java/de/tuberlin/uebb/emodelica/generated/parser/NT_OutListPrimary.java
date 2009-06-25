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

public class NT_OutListPrimary extends Absy implements NT_Primary {

	/* constructor */
	public NT_OutListPrimary() {
		super();
	}

	public String toString() {
		return "NT_OutListPrimary";
	}


/* attributes */
	 private NT_Output_Expression_List nt_output_expression_list = null;


/* getters and setters */

	public void setNt_output_expression_list(NT_Output_Expression_List nt_output_expression_list) {
		this.nt_output_expression_list = nt_output_expression_list;
	}

	public NT_Output_Expression_List getNt_output_expression_list() {
		return nt_output_expression_list;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_OutListPrimary";}

}
