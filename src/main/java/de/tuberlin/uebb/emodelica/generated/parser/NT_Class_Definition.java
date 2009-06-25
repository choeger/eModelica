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

public class NT_Class_Definition extends Absy implements NT_ClassDefinitionOrMultipleComponentClause, NT_ClassDefinitionOrSingleComponentClause {

	/* constructor */
	public NT_Class_Definition() {
		super();
	}

	public String toString() {
		return "NT_Class_Definition";
	}


/* attributes */
	 private NT_Encapsulated nt_encapsulated = null;
	 private NT_Partial nt_partial = null;
	 private NT_Class_Def_KW nt_class_def_kw = null;
	 private NT_Specifier nt_specifier = null;


/* getters and setters */

	public void setNt_encapsulated(NT_Encapsulated nt_encapsulated) {
		this.nt_encapsulated = nt_encapsulated;
	}

	public NT_Encapsulated getNt_encapsulated() {
		return nt_encapsulated;
	}

	public void setNt_partial(NT_Partial nt_partial) {
		this.nt_partial = nt_partial;
	}

	public NT_Partial getNt_partial() {
		return nt_partial;
	}

	public void setNt_class_def_kw(NT_Class_Def_KW nt_class_def_kw) {
		this.nt_class_def_kw = nt_class_def_kw;
	}

	public NT_Class_Def_KW getNt_class_def_kw() {
		return nt_class_def_kw;
	}

	public void setNt_specifier(NT_Specifier nt_specifier) {
		this.nt_specifier = nt_specifier;
	}

	public NT_Specifier getNt_specifier() {
		return nt_specifier;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Class_Definition";}

}
