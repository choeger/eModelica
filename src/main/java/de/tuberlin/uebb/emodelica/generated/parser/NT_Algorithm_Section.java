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

public class NT_Algorithm_Section extends Absy implements NT_Composition_Element {

	/* constructor */
	public NT_Algorithm_Section() {
		super();
	}

	public String toString() {
		return "NT_Algorithm_Section";
	}


/* attributes */
	 private NT_Initial nt_initial = null;
	 private ListToken nt_algorithmsectionelement = null;


/* getters and setters */

	public void setNt_initial(NT_Initial nt_initial) {
		this.nt_initial = nt_initial;
	}

	public NT_Initial getNt_initial() {
		return nt_initial;
	}

	public void setNt_algorithmsectionelement(ListToken nt_algorithmsectionelement) {
		this.nt_algorithmsectionelement = nt_algorithmsectionelement;
	}

	public ListToken getNt_algorithmsectionelement() {
		return nt_algorithmsectionelement;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Algorithm_Section";}

}
