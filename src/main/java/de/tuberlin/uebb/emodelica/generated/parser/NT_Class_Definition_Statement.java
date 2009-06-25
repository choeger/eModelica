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

public class NT_Class_Definition_Statement extends Absy {

	/* constructor */
	public NT_Class_Definition_Statement() {
		super();
	}

	public String toString() {
		return "NT_Class_Definition_Statement";
	}


/* attributes */
	 private NT_Final nt_final = null;
	 private NT_Class_Definition nt_class_definition = null;


/* getters and setters */

	public void setNt_final(NT_Final nt_final) {
		this.nt_final = nt_final;
	}

	public NT_Final getNt_final() {
		return nt_final;
	}

	public void setNt_class_definition(NT_Class_Definition nt_class_definition) {
		this.nt_class_definition = nt_class_definition;
	}

	public NT_Class_Definition getNt_class_definition() {
		return nt_class_definition;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Class_Definition_Statement";}

}
