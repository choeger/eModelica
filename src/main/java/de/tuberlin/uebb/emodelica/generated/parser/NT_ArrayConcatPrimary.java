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

public class NT_ArrayConcatPrimary extends Absy implements NT_Primary {

	/* constructor */
	public NT_ArrayConcatPrimary() {
		super();
	}

	public String toString() {
		return "NT_ArrayConcatPrimary";
	}


/* attributes */
	 private ListToken nt_expression_list = null;


/* getters and setters */

	public void setNt_expression_list(ListToken nt_expression_list) {
		this.nt_expression_list = nt_expression_list;
	}

	public ListToken getNt_expression_list() {
		return nt_expression_list;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_ArrayConcatPrimary";}

}
