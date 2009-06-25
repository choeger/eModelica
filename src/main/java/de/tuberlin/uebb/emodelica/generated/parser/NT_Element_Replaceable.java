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

public class NT_Element_Replaceable extends Absy implements NT_ModArgument, NT_Element_Redeclaration_Suffix {

	/* constructor */
	public NT_Element_Replaceable() {
		super();
	}

	public String toString() {
		return "NT_Element_Replaceable";
	}


/* attributes */
	 private NT_ClassDefinitionOrSingleComponentClause nt_classdefinitionorsinglecomponentclause = null;
	 private NT_Constraining_Clause nt_constraining_clause = null;


/* getters and setters */

	public void setNt_classdefinitionorsinglecomponentclause(NT_ClassDefinitionOrSingleComponentClause nt_classdefinitionorsinglecomponentclause) {
		this.nt_classdefinitionorsinglecomponentclause = nt_classdefinitionorsinglecomponentclause;
	}

	public NT_ClassDefinitionOrSingleComponentClause getNt_classdefinitionorsinglecomponentclause() {
		return nt_classdefinitionorsinglecomponentclause;
	}

	public void setNt_constraining_clause(NT_Constraining_Clause nt_constraining_clause) {
		this.nt_constraining_clause = nt_constraining_clause;
	}

	public NT_Constraining_Clause getNt_constraining_clause() {
		return nt_constraining_clause;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Element_Replaceable";}

}
