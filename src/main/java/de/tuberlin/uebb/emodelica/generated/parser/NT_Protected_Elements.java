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

public class NT_Protected_Elements extends Absy implements NT_Composition_Element {

	/* constructor */
	public NT_Protected_Elements() {
		super();
	}

	public String toString() {
		return "NT_Protected_Elements";
	}


/* attributes */
	 private ListToken nt_element_ = null;


/* getters and setters */

	public void setNt_element_(ListToken nt_element_) {
		this.nt_element_ = nt_element_;
	}

	public ListToken getNt_element_() {
		return nt_element_;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Protected_Elements";}

}
