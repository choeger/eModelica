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

public class NT_Constraining_Clause extends Absy {

	/* constructor */
	public NT_Constraining_Clause() {
		super();
	}

	public String toString() {
		return "NT_Constraining_Clause";
	}


/* attributes */
	 private NT_Name nt_name = null;
	 private NT_Class_Modification nt_class_modification = null;


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


/* null-pointer safe name */
public String getAbsyName() { return "NT_Constraining_Clause";}

}
