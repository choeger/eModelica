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

public class NT_Component_Reference extends Absy {

	/* constructor */
	public NT_Component_Reference() {
		super();
	}

	public String toString() {
		return "NT_Component_Reference";
	}


/* attributes */
	 private ListToken nt_referencepart = null;


/* getters and setters */

	public void setNt_referencepart(ListToken nt_referencepart) {
		this.nt_referencepart = nt_referencepart;
	}

	public ListToken getNt_referencepart() {
		return nt_referencepart;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Component_Reference";}

}
