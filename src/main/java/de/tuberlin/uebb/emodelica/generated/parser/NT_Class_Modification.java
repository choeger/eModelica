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

public class NT_Class_Modification extends Absy {

	/* constructor */
	public NT_Class_Modification() {
		super();
	}

	public String toString() {
		return "NT_Class_Modification";
	}


/* attributes */
	 private NT_ModArgumentList nt_modargumentlist = null;


/* getters and setters */

	public void setNt_modargumentlist(NT_ModArgumentList nt_modargumentlist) {
		this.nt_modargumentlist = nt_modargumentlist;
	}

	public NT_ModArgumentList getNt_modargumentlist() {
		return nt_modargumentlist;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Class_Modification";}

}
