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

public class NT_StatementPart extends Absy implements NT_AlgorithmSectionElement {

	/* constructor */
	public NT_StatementPart() {
		super();
	}

	public String toString() {
		return "NT_StatementPart";
	}


/* attributes */
	 private NT_Statement nt_statement = null;


/* getters and setters */

	public void setNt_statement(NT_Statement nt_statement) {
		this.nt_statement = nt_statement;
	}

	public NT_Statement getNt_statement() {
		return nt_statement;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_StatementPart";}

}
