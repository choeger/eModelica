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

public class NT_SingleComponentClause extends Absy implements NT_ClassDefinitionOrSingleComponentClause {

	/* constructor */
	public NT_SingleComponentClause() {
		super();
	}

	public String toString() {
		return "NT_SingleComponentClause";
	}


/* attributes */
	 private NT_Type_Prefix nt_type_prefix = null;
	 private NT_Name nt_name = null;
	 private NT_SingleComponentDeclaration nt_singlecomponentdeclaration = null;


/* getters and setters */

	public void setNt_type_prefix(NT_Type_Prefix nt_type_prefix) {
		this.nt_type_prefix = nt_type_prefix;
	}

	public NT_Type_Prefix getNt_type_prefix() {
		return nt_type_prefix;
	}

	public void setNt_name(NT_Name nt_name) {
		this.nt_name = nt_name;
	}

	public NT_Name getNt_name() {
		return nt_name;
	}

	public void setNt_singlecomponentdeclaration(NT_SingleComponentDeclaration nt_singlecomponentdeclaration) {
		this.nt_singlecomponentdeclaration = nt_singlecomponentdeclaration;
	}

	public NT_SingleComponentDeclaration getNt_singlecomponentdeclaration() {
		return nt_singlecomponentdeclaration;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_SingleComponentClause";}

}
