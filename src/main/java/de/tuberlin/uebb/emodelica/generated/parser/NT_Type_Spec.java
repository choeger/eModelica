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

public class NT_Type_Spec extends Absy implements NT_Specifier {

	/* constructor */
	public NT_Type_Spec() {
		super();
	}

	public String toString() {
		return "NT_Type_Spec";
	}


/* attributes */
	 private NT_Identifier nt_identifier = null;
	 private NT_Type_Prefix nt_type_prefix = null;
	 private NT_Name nt_name = null;
	 private NT_Array_Subscripts nt_array_subscripts = null;
	 private NT_Class_Modification nt_class_modification = null;
	 private NT_String_Comment nt_string_comment = null;
	 private NT_Annotation nt_annotation = null;


/* getters and setters */

	public void setNt_identifier(NT_Identifier nt_identifier) {
		this.nt_identifier = nt_identifier;
	}

	public NT_Identifier getNt_identifier() {
		return nt_identifier;
	}

	public void setNt_type_prefix(NT_Type_Prefix nt_type_prefix) {
		this.nt_type_prefix = nt_type_prefix;
	}

	public NT_Type_Prefix getNt_type_prefix() {
		return nt_type_prefix;
	}

	public void setNt_name(NT_Name nt_name) {
		this.nt_name = nt_name;
	}

	public NT_Name getNt_name() {
		return nt_name;
	}

	public void setNt_array_subscripts(NT_Array_Subscripts nt_array_subscripts) {
		this.nt_array_subscripts = nt_array_subscripts;
	}

	public NT_Array_Subscripts getNt_array_subscripts() {
		return nt_array_subscripts;
	}

	public void setNt_class_modification(NT_Class_Modification nt_class_modification) {
		this.nt_class_modification = nt_class_modification;
	}

	public NT_Class_Modification getNt_class_modification() {
		return nt_class_modification;
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
public String getAbsyName() { return "NT_Type_Spec";}

}
