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

public class NT_RenamingImport extends Absy implements NT_Import_Clause {

	/* constructor */
	public NT_RenamingImport() {
		super();
	}

	public String toString() {
		return "NT_RenamingImport";
	}


/* attributes */
	 private NT_Identifier nt_identifier = null;
	 private NT_Name nt_name = null;


/* getters and setters */

	public void setNt_identifier(NT_Identifier nt_identifier) {
		this.nt_identifier = nt_identifier;
	}

	public NT_Identifier getNt_identifier() {
		return nt_identifier;
	}

	public void setNt_name(NT_Name nt_name) {
		this.nt_name = nt_name;
	}

	public NT_Name getNt_name() {
		return nt_name;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_RenamingImport";}

}
