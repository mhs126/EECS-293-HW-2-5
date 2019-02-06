package Parser;

import java.util.List;
import java.util.Optional;

enum NonTerminalSymbol implements Symbol {

    EXPRESSION, EXPRESSION_TAIL, TERM, TERM_TAIL, UNARY, FACTOR;

    public ParseState parse(List<Token> list) {
        return ParseState.build(null, null);
    }
}
