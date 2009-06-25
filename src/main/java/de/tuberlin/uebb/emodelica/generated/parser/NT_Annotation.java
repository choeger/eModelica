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

public class NT_Annotation extends Absy {

	/* constructor */
	public NT_Annotation() {
		super();
	}

	public String toString() {
		return "NT_Annotation";
	}


/* attributes */
	 private NT_Class_Modification nt_class_modification = null;


/* getters and setters */

	public void setNt_class_modification(NT_Class_Modification nt_class_modification) {
		this.nt_class_modification = nt_class_modification;
	}

	public NT_Class_Modification getNt_class_modification() {
		return nt_class_modification;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Annotation";}

}
