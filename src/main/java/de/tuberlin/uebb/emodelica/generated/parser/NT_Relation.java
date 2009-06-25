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

public class NT_Relation extends Absy {

	/* constructor */
	public NT_Relation() {
		super();
	}

	public String toString() {
		return "NT_Relation";
	}


/* attributes */
	 private NT_Arithmetic_Expression nt_arithmetic_expression = null;
	 private NT_RelationSuffix nt_relationsuffix = null;


/* getters and setters */

	public void setNt_arithmetic_expression(NT_Arithmetic_Expression nt_arithmetic_expression) {
		this.nt_arithmetic_expression = nt_arithmetic_expression;
	}

	public NT_Arithmetic_Expression getNt_arithmetic_expression() {
		return nt_arithmetic_expression;
	}

	public void setNt_relationsuffix(NT_RelationSuffix nt_relationsuffix) {
		this.nt_relationsuffix = nt_relationsuffix;
	}

	public NT_RelationSuffix getNt_relationsuffix() {
		return nt_relationsuffix;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Relation";}

}
