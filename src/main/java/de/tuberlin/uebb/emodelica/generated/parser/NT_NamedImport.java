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

public class NT_NamedImport extends Absy implements NT_Import_Clause {

	/* constructor */
	public NT_NamedImport() {
		super();
	}

	public String toString() {
		return "NT_NamedImport";
	}


/* attributes */
	 private NT_Name nt_name = null;
	 private NT_AllElementExt nt_allelementext = null;


/* getters and setters */

	public void setNt_name(NT_Name nt_name) {
		this.nt_name = nt_name;
	}

	public NT_Name getNt_name() {
		return nt_name;
	}

	public void setNt_allelementext(NT_AllElementExt nt_allelementext) {
		this.nt_allelementext = nt_allelementext;
	}

	public NT_AllElementExt getNt_allelementext() {
		return nt_allelementext;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_NamedImport";}

}
