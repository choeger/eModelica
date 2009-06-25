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

public class NT_External_Comp_Element extends Absy {

	/* constructor */
	public NT_External_Comp_Element() {
		super();
	}

	public String toString() {
		return "NT_External_Comp_Element";
	}


/* attributes */
	 private NT_Language_Specification nt_language_specification = null;
	 private NT_External_Function_Call nt_external_function_call = null;
	 private NT_Annotation nt_annotation = null;
	 private NT_Annotation_Part nt_annotation_part = null;


/* getters and setters */

	public void setNt_language_specification(NT_Language_Specification nt_language_specification) {
		this.nt_language_specification = nt_language_specification;
	}

	public NT_Language_Specification getNt_language_specification() {
		return nt_language_specification;
	}

	public void setNt_external_function_call(NT_External_Function_Call nt_external_function_call) {
		this.nt_external_function_call = nt_external_function_call;
	}

	public NT_External_Function_Call getNt_external_function_call() {
		return nt_external_function_call;
	}

	public void setNt_annotation(NT_Annotation nt_annotation) {
		this.nt_annotation = nt_annotation;
	}

	public NT_Annotation getNt_annotation() {
		return nt_annotation;
	}

	public void setNt_annotation_part(NT_Annotation_Part nt_annotation_part) {
		this.nt_annotation_part = nt_annotation_part;
	}

	public NT_Annotation_Part getNt_annotation_part() {
		return nt_annotation_part;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_External_Comp_Element";}

}
