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

public class NT_Component_Declaration extends Absy {

	/* constructor */
	public NT_Component_Declaration() {
		super();
	}

	public String toString() {
		return "NT_Component_Declaration";
	}


/* attributes */
	 private NT_Declaration nt_declaration = null;
	 private NT_Conditional_Attribute nt_conditional_attribute = null;
	 private NT_String_Comment nt_string_comment = null;
	 private NT_Annotation nt_annotation = null;


/* getters and setters */

	public void setNt_declaration(NT_Declaration nt_declaration) {
		this.nt_declaration = nt_declaration;
	}

	public NT_Declaration getNt_declaration() {
		return nt_declaration;
	}

	public void setNt_conditional_attribute(NT_Conditional_Attribute nt_conditional_attribute) {
		this.nt_conditional_attribute = nt_conditional_attribute;
	}

	public NT_Conditional_Attribute getNt_conditional_attribute() {
		return nt_conditional_attribute;
	}

	public void setNt_string_comment(NT_String_Comment nt_string_comment) {
		this.nt_string_comment = nt_string_comment;
	}

	public NT_String_Comment getNt_string_comment() {
		return nt_string_comment;
	}

	public void setNt_annotation(NT_Annotation nt_annotation) {
		this.nt_annotation = nt_annotation;
	}

	public NT_Annotation getNt_annotation() {
		return nt_annotation;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Component_Declaration";}

}
