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

public class NT_For_Index extends Absy {

	/* constructor */
	public NT_For_Index() {
		super();
	}

	public String toString() {
		return "NT_For_Index";
	}


/* attributes */
	 private NT_Identifier nt_identifier = null;
	 private NT_IndexSuffix nt_indexsuffix = null;


/* getters and setters */

	public void setNt_identifier(NT_Identifier nt_identifier) {
		this.nt_identifier = nt_identifier;
	}

	public NT_Identifier getNt_identifier() {
		return nt_identifier;
	}

	public void setNt_indexsuffix(NT_IndexSuffix nt_indexsuffix) {
		this.nt_indexsuffix = nt_indexsuffix;
	}

	public NT_IndexSuffix getNt_indexsuffix() {
		return nt_indexsuffix;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_For_Index";}

}
