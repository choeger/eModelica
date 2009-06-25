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

public class NT_Enum_Spec_Colon extends Absy implements NT_Enum_Spec_Part {

	/* constructor */
	public NT_Enum_Spec_Colon() {
		super();
	}

	public String toString() {
		return getValue().getValue();
	}


/* attributes */
	 private Terminal value = null;


/* getters and setters */

	public void setValue(Terminal value) {
		this.value = value;
	}

	public Terminal getValue() {
		return value;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Enum_Spec_Colon";}

}
