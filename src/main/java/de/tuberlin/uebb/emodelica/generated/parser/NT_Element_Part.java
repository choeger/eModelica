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

public class NT_Element_Part extends Absy implements NT_Element_ {

	/* constructor */
	public NT_Element_Part() {
		super();
	}

	public String toString() {
		return "NT_Element_Part";
	}


/* attributes */
	 private NT_Element nt_element = null;


/* getters and setters */

	public void setNt_element(NT_Element nt_element) {
		this.nt_element = nt_element;
	}

	public NT_Element getNt_element() {
		return nt_element;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Element_Part";}

}
