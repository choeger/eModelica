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

public class NT_Annotation_Part extends Absy implements NT_Element_, NT_EquationSectionElement, NT_AlgorithmSectionElement {

	/* constructor */
	public NT_Annotation_Part() {
		super();
	}

	public String toString() {
		return "NT_Annotation_Part";
	}


/* attributes */
	 private NT_Annotation nt_annotation = null;


/* getters and setters */

	public void setNt_annotation(NT_Annotation nt_annotation) {
		this.nt_annotation = nt_annotation;
	}

	public NT_Annotation getNt_annotation() {
		return nt_annotation;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Annotation_Part";}

}
