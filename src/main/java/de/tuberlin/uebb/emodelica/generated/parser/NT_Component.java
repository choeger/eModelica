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

public class NT_Component extends Absy {

	/* constructor */
	public NT_Component() {
		super();
	}

	public String toString() {
		return "NT_Component";
	}


/* attributes */
	 private NT_Replaceable nt_replaceable = null;
	 private NT_ClassDefinitionOrMultipleComponentClause nt_classdefinitionormultiplecomponentclause = null;
	 private NT_Constraining_Clause_With_Comment nt_constraining_clause_with_comment = null;


/* getters and setters */

	public void setNt_replaceable(NT_Replaceable nt_replaceable) {
		this.nt_replaceable = nt_replaceable;
	}

	public NT_Replaceable getNt_replaceable() {
		return nt_replaceable;
	}

	public void setNt_classdefinitionormultiplecomponentclause(NT_ClassDefinitionOrMultipleComponentClause nt_classdefinitionormultiplecomponentclause) {
		this.nt_classdefinitionormultiplecomponentclause = nt_classdefinitionormultiplecomponentclause;
	}

	public NT_ClassDefinitionOrMultipleComponentClause getNt_classdefinitionormultiplecomponentclause() {
		return nt_classdefinitionormultiplecomponentclause;
	}

	public void setNt_constraining_clause_with_comment(NT_Constraining_Clause_With_Comment nt_constraining_clause_with_comment) {
		this.nt_constraining_clause_with_comment = nt_constraining_clause_with_comment;
	}

	public NT_Constraining_Clause_With_Comment getNt_constraining_clause_with_comment() {
		return nt_constraining_clause_with_comment;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Component";}

}
