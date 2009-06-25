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

public class NT_Extends_Clause extends Absy implements NT_Element {

	/* constructor */
	public NT_Extends_Clause() {
		super();
	}

	public String toString() {
		return "NT_Extends_Clause";
	}


/* attributes */
	 private NT_Name nt_name = null;
	 private NT_Class_Modification nt_class_modification = null;
	 private NT_Annotation nt_annotation = null;


/* getters and setters */

	public void setNt_name(NT_Name nt_name) {
		this.nt_name = nt_name;
	}

	public NT_Name getNt_name() {
		return nt_name;
	}

	public void setNt_class_modification(NT_Class_Modification nt_class_modification) {
		this.nt_class_modification = nt_class_modification;
	}

	public NT_Class_Modification getNt_class_modification() {
		return nt_class_modification;
	}

	public void setNt_annotation(NT_Annotation nt_annotation) {
		this.nt_annotation = nt_annotation;
	}

	public NT_Annotation getNt_annotation() {
		return nt_annotation;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Extends_Clause";}

}
