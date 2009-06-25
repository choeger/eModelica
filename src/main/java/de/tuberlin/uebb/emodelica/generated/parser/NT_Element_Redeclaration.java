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

public class NT_Element_Redeclaration extends Absy implements NT_ModArgument {

	/* constructor */
	public NT_Element_Redeclaration() {
		super();
	}

	public String toString() {
		return "NT_Element_Redeclaration";
	}


/* attributes */
	 private NT_Each nt_each = null;
	 private NT_Final nt_final = null;
	 private NT_Element_Redeclaration_Suffix nt_element_redeclaration_suffix = null;


/* getters and setters */

	public void setNt_each(NT_Each nt_each) {
		this.nt_each = nt_each;
	}

	public NT_Each getNt_each() {
		return nt_each;
	}

	public void setNt_final(NT_Final nt_final) {
		this.nt_final = nt_final;
	}

	public NT_Final getNt_final() {
		return nt_final;
	}

	public void setNt_element_redeclaration_suffix(NT_Element_Redeclaration_Suffix nt_element_redeclaration_suffix) {
		this.nt_element_redeclaration_suffix = nt_element_redeclaration_suffix;
	}

	public NT_Element_Redeclaration_Suffix getNt_element_redeclaration_suffix() {
		return nt_element_redeclaration_suffix;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Element_Redeclaration";}

}
