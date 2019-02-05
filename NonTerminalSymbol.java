package Parser;

import java.util.List;
import java.util.Optional;

enum NonTerminalSymbol implements Symbol {

    EXPRESSION, EXPRESSION_TAIL, TERM, TERM_TAIL, UNARY, FACTOR;

    public ParseState parse(List<Token> list){
        return ParseState.build(null, null);
    }

    final Optional<Node> parseInput(List<Token> input){
        if(input == null)
            throw new NullPointerException("Null Input");
        else{
            if(input.get(0).getType() == EXPRESSION)
        }
    }

}
