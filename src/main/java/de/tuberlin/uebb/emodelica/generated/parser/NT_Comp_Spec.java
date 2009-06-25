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

public class NT_Comp_Spec extends Absy implements NT_Specifier {

	/* constructor */
	public NT_Comp_Spec() {
		super();
	}

	public String toString() {
		return "NT_Comp_Spec";
	}


/* attributes */
	 private NT_Identifier nt_identifier = null;
	 private NT_String_Comment nt_string_comment = null;
	 private NT_Composition nt_composition = null;
	 private NT_Identifier nt_identifier1 = null;


/* getters and setters */

	public void setNt_identifier(NT_Identifier nt_identifier) {
		this.nt_identifier = nt_identifier;
	}

	public NT_Identifier getNt_identifier() {
		return nt_identifier;
	}

	public void setNt_string_comment(NT_String_Comment nt_string_comment) {
		this.nt_string_comment = nt_string_comment;
	}

	public NT_String_Comment getNt_string_comment() {
		return nt_string_comment;
	}

	public void setNt_composition(NT_Composition nt_composition) {
		this.nt_composition = nt_composition;
	}

	public NT_Composition getNt_composition() {
		return nt_composition;
	}

	public void setNt_identifier1(NT_Identifier nt_identifier1) {
		this.nt_identifier1 = nt_identifier1;
	}

	public NT_Identifier getNt_identifier1() {
		return nt_identifier1;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Comp_Spec";}

}
