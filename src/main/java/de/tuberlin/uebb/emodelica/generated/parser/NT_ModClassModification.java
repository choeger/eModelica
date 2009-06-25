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

public class NT_ModClassModification extends Absy implements NT_Modification {

	/* constructor */
	public NT_ModClassModification() {
		super();
	}

	public String toString() {
		return "NT_ModClassModification";
	}


/* attributes */
	 private NT_Class_Modification nt_class_modification = null;
	 private NT_ModBindExpression nt_modbindexpression = null;


/* getters and setters */

	public void setNt_class_modification(NT_Class_Modification nt_class_modification) {
		this.nt_class_modification = nt_class_modification;
	}

	public NT_Class_Modification getNt_class_modification() {
		return nt_class_modification;
	}

	public void setNt_modbindexpression(NT_ModBindExpression nt_modbindexpression) {
		this.nt_modbindexpression = nt_modbindexpression;
	}

	public NT_ModBindExpression getNt_modbindexpression() {
		return nt_modbindexpression;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_ModClassModification";}

}
