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

public class NT_Redeclare extends Absy {

	/* constructor */
	public NT_Redeclare() {
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
public String getAbsyName() { return "NT_Redeclare";}

}
