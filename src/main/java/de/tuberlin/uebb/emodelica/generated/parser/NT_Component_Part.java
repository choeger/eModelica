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

public class NT_Component_Part extends Absy implements NT_Element {

	/* constructor */
	public NT_Component_Part() {
		super();
	}

	public String toString() {
		return "NT_Component_Part";
	}


/* attributes */
	 private NT_Redeclare nt_redeclare = null;
	 private NT_Final nt_final = null;
	 private NT_Inner nt_inner = null;
	 private NT_Outer nt_outer = null;
	 private NT_Component nt_component = null;


/* getters and setters */

	public void setNt_redeclare(NT_Redeclare nt_redeclare) {
		this.nt_redeclare = nt_redeclare;
	}

	public NT_Redeclare getNt_redeclare() {
		return nt_redeclare;
	}

	public void setNt_final(NT_Final nt_final) {
		this.nt_final = nt_final;
	}

	public NT_Final getNt_final() {
		return nt_final;
	}

	public void setNt_inner(NT_Inner nt_inner) {
		this.nt_inner = nt_inner;
	}

	public NT_Inner getNt_inner() {
		return nt_inner;
	}

	public void setNt_outer(NT_Outer nt_outer) {
		this.nt_outer = nt_outer;
	}

	public NT_Outer getNt_outer() {
		return nt_outer;
	}

	public void setNt_component(NT_Component nt_component) {
		this.nt_component = nt_component;
	}

	public NT_Component getNt_component() {
		return nt_component;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Component_Part";}

}
