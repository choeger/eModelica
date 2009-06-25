# modelica.lex
# lexer definitions for modelica
#
# Keywords
1	\s+	DROP
# single line comment
1	//.*$	DROP
# multiline comment (boah, hard stuff)
1	(/\*)[(?<!/)/[^/]]*?(\*/)	DROP
1	within|final|encapsulated|partial|class|model|record|block|expandable|connector|type|package|function|end|enumeration|der|extends|public|protected|external|redeclare|final|inner|outer|replaceable|import|constrainedby|flow|discrete|parameter|constant|input|output|if|each|initial|equation|algorithm|break|return|then|else((if)|(when))?|for|loop|in|while|when|connect|or|and|not|false|true|annotation	IDENTITY
# Operators (multichar operators seperate, match longest hit)
1	(<=)|(>=)|(==)|(<>)|(\.\+)|(\.\-)|(\.\*)|(\./)|(\.\^)	IDENTITY
# single char operators
1	<|>|\+|\-|\*|/|\^	IDENTITY
# brackets
1	\(|\)|\{|\}|\[|\]	IDENTITY
# syntactic stuff
1	(:=)|=|\.|,|;|:|=	IDENTITY
# identifier (priority class 2 as they conflict with keywords), normal or 'quoted'
2	((_|\p{Alpha})+(_|\p{Alnum})*)|('[(?<=\\)'[^']]*?(?<!\\)')	IDENT
# in the specification no number may look like .12345, but in the Standard library we see that
2	(\.\d+|(\d+(\.\d*)?))((e|E)(\+|\-)?\d+)?	UNSIGNED_NUMBER
# whoa, that is hard to read...
2	"[(?<=\\")"[^"]]*?(?<!\\)"	STRING
