package Parser;

import java.util.*;

import static java.util.Optional.empty;
import static java.util.Optional.of;

enum NonTerminalSymbol implements Symbol {

    //Non-Terminal types
    EXPRESSION, EXPRESSION_TAIL, TERM, TERM_TAIL, UNARY, FACTOR;

    static Map<NonTerminalSymbol, List<SymbolSequence>> nonTerminalSymbolsMap = new HashMap<>();

    static {
        //Creates the list for expression
        List<SymbolSequence> expressionList = new ArrayList<>();
        SymbolSequence expression1 = SymbolSequence.build(TERM, EXPRESSION_TAIL);
        expressionList.add(expression1);
        nonTerminalSymbolsMap.put(EXPRESSION, expressionList);

        //Creates the list for expression_tail
        List<SymbolSequence> expression_tailList = new ArrayList<>();
        SymbolSequence expression_tail1 = SymbolSequence.build(TerminalSymbol.PLUS, TERM, EXPRESSION_TAIL);
        SymbolSequence expression_tail2 = SymbolSequence.build(TerminalSymbol.MINUS, TERM, EXPRESSION_TAIL);
        expression_tailList.add(expression_tail1);
        expression_tailList.add(expression_tail2);
        expression_tailList.add(SymbolSequence.EPSILON);
        nonTerminalSymbolsMap.put(EXPRESSION_TAIL, expression_tailList);

        //Creates the list for term
        List<SymbolSequence> termList = new ArrayList<>();
        SymbolSequence term1 = SymbolSequence.build(UNARY, TERM_TAIL);
        termList.add(term1);
        nonTerminalSymbolsMap.put(TERM, termList);

        //Creates the list for term_list
        List<SymbolSequence> term_tailList = new ArrayList<>();
        SymbolSequence term_tail1 = SymbolSequence.build(TerminalSymbol.TIMES, UNARY, TERM_TAIL);
        SymbolSequence term_tail2 = SymbolSequence.build(TerminalSymbol.DIVIDE, UNARY, TERM_TAIL);
        term_tailList.add(term_tail1);
        term_tailList.add(term_tail2);
        term_tailList.add(SymbolSequence.EPSILON);
        nonTerminalSymbolsMap.put(TERM_TAIL, term_tailList);

        //creates the list for unary
        List<SymbolSequence> unaryList = new ArrayList<>();
        SymbolSequence unary1 = SymbolSequence.build(TerminalSymbol.MINUS, FACTOR);
        SymbolSequence unary2 = SymbolSequence.build(FACTOR);
        unaryList.add(unary1);
        unaryList.add(unary2);
        nonTerminalSymbolsMap.put(UNARY, unaryList);

        //creates list for factor
        List<SymbolSequence> factorList = new ArrayList<>();
        SymbolSequence factor1 = SymbolSequence.build(TerminalSymbol.OPEN, EXPRESSION, TerminalSymbol.CLOSE);
        SymbolSequence factor2 = SymbolSequence.build(TerminalSymbol.VARIABLE);
        factorList.add(factor1);
        factorList.add(factor2);
        nonTerminalSymbolsMap.put(FACTOR, factorList);
    }

    public ParseState parse(List<Token> list) {
        Objects.requireNonNull(list, "Input list is null, please enter a valid list");
        for (SymbolSequence symbolSequence : nonTerminalSymbolsMap.get(this)) {
            ParseState p = symbolSequence.match(list);
            if (p.isSuccess()) {
                return p;
            }
            //else it is not successful

        }
        return ParseState.FAILURE;
    }

    static final Optional<Node> parseInput (List<Token> input){
        Objects.requireNonNull(input, "Input list is null, please enter a valid list");
        Optional<Node> optionalNode = new Optional<>();
        ParseState p = EXPRESSION.parse(input);
        if(p.isSuccess() && p.hasNoRemainder()) {
            return optionalNode.of(p.getNode());
        }
        else {
            return optionalNode.empty();
        }

    }
}

