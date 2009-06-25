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

public class NT_ElseEquation extends Absy {

	/* constructor */
	public NT_ElseEquation() {
		super();
	}

	public String toString() {
		return "NT_ElseEquation";
	}


/* attributes */
	 private ListToken nt_equationpart = null;


/* getters and setters */

	public void setNt_equationpart(ListToken nt_equationpart) {
		this.nt_equationpart = nt_equationpart;
	}

	public ListToken getNt_equationpart() {
		return nt_equationpart;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_ElseEquation";}

}
