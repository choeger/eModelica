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

public class NT_Constraining_Clause_With_Comment extends Absy {

	/* constructor */
	public NT_Constraining_Clause_With_Comment() {
		super();
	}

	public String toString() {
		return "NT_Constraining_Clause_With_Comment";
	}


/* attributes */
	 private NT_Constraining_Clause nt_constraining_clause = null;
	 private NT_String_Comment nt_string_comment = null;
	 private NT_Annotation nt_annotation = null;


/* getters and setters */

	public void setNt_constraining_clause(NT_Constraining_Clause nt_constraining_clause) {
		this.nt_constraining_clause = nt_constraining_clause;
	}

	public NT_Constraining_Clause getNt_constraining_clause() {
		return nt_constraining_clause;
	}

	public void setNt_string_comment(NT_String_Comment nt_string_comment) {
		this.nt_string_comment = nt_string_comment;
	}

	public NT_String_Comment getNt_string_comment() {
		return nt_string_comment;
	}

	public void setNt_annotation(NT_Annotation nt_annotation) {
		this.nt_annotation = nt_annotation;
	}

	public NT_Annotation getNt_annotation() {
		return nt_annotation;
	}


/* null-pointer safe name */
public String getAbsyName() { return "NT_Constraining_Clause_With_Comment";}

}
