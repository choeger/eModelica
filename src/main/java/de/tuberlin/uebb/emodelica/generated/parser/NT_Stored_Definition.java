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

public class NT_Stored_Definition extends Absy {

	/* constructor */
	public NT_Stored_Definition() {
		super();
	}

	public String toString() {
		return "NT_Stored_Definition";
	}


/* attributes */
	 private NT_Within_Statement nt_within_statement = null;
	 private ListToken nt_class_definition_statement = null;


/* getters and setters */

	public void setNt_within_statement(NT_Within_Statement nt_within_statement) {
		this.nt_within_statement = nt_within_statement;
	}

	public NT_Within_Statement getNt_within_statement() {
		return nt_within_statement;
	}

	public void setNt_class_definition_statement(ListToken nt_class_definition_statement) {
		this.nt_class_definition_statement = nt_class_definition_statement;
	}

	public ListToken getNt_class_definition_statement() {
		return nt_class_definition_statement;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Stored_Definition";}

}
