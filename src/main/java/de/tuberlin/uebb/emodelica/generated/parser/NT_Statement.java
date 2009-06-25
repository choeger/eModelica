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

public class NT_Statement extends Absy {

	/* constructor */
	public NT_Statement() {
		super();
	}

	public String toString() {
		return "NT_Statement";
	}


/* attributes */
	 private NT_StatementContent nt_statementcontent = null;
	 private NT_String_Comment nt_string_comment = null;
	 private NT_Annotation nt_annotation = null;


/* getters and setters */

	public void setNt_statementcontent(NT_StatementContent nt_statementcontent) {
		this.nt_statementcontent = nt_statementcontent;
	}

	public NT_StatementContent getNt_statementcontent() {
		return nt_statementcontent;
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
public String getAbsyName() { return "NT_Statement";}

}
