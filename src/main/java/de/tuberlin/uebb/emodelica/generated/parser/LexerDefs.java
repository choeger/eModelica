package de.tuberlin.uebb.emodelica.generated.parser;
import de.tuberlin.uebb.page.lexer.PriorityClass;
import de.tuberlin.uebb.page.lexer.LexerChoice;
import java.util.ArrayList;
public class LexerDefs { 
	public static ArrayList<PriorityClass> lexerDefs() {
		ArrayList<PriorityClass> prioList = new ArrayList<PriorityClass>(); 
		ArrayList<LexerChoice> choices = new ArrayList<LexerChoice>(); 
		choices.clear(); 
		choices.add(new LexerChoice("\\s+", "DROP")); 
		choices.add(new LexerChoice("//.*$", "DROP")); 
		choices.add(new LexerChoice("(/\\*)[(?<!/)/[^/]]*?(\\*/)", "DROP")); 
		choices.add(new LexerChoice("within|final|encapsulated|partial|class|model|record|block|expandable|connector|type|package|function|end|enumeration|der|extends|public|protected|external|redeclare|final|inner|outer|replaceable|import|constrainedby|flow|discrete|parameter|constant|input|output|if|each|initial|equation|algorithm|break|return|then|else((if)|(when))?|for|loop|in|while|when|connect|or|and|not|false|true|annotation", "IDENTITY")); 
		choices.add(new LexerChoice("(<=)|(>=)|(==)|(<>)|(\\.\\+)|(\\.\\-)|(\\.\\*)|(\\./)|(\\.\\^)", "IDENTITY")); 
		choices.add(new LexerChoice("<|>|\\+|\\-|\\*|/|\\^", "IDENTITY")); 
		choices.add(new LexerChoice("\\(|\\)|\\{|\\}|\\[|\\]", "IDENTITY")); 
		choices.add(new LexerChoice("(:=)|=|\\.|,|;|:|=", "IDENTITY")); 
		prioList.add(new PriorityClass(new ArrayList<LexerChoice>(choices))); 
		choices.clear(); 
		choices.add(new LexerChoice("((_|\\p{Alpha})+(_|\\p{Alnum})*)|('[(?<=\\\\)'[^']]*?(?<!\\\\)')", "IDENT")); 
		choices.add(new LexerChoice("(\\.\\d+|(\\d+(\\.\\d*)?))((e|E)(\\+|\\-)?\\d+)?", "UNSIGNED_NUMBER")); 
		choices.add(new LexerChoice("\"[(?<=\\\\\")\"[^\"]]*?(?<!\\\\)\"", "STRING")); 
		prioList.add(new PriorityClass(new ArrayList<LexerChoice>(choices))); 

		return prioList;
	}

}
