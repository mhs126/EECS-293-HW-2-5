package Parser;

import com.sun.org.apache.xpath.internal.operations.Minus;

import java.util.*;

import static java.util.Optional.empty;
import static java.util.Optional.of;

enum NonTerminalSymbol implements Symbol {

    //Non-Terminal types
    EXPRESSION, EXPRESSION_TAIL, TERM, TERM_TAIL, UNARY, FACTOR;

    /*
    Creates the map with NonTerminalSymbol key and map value
    Creates six maps, one for each NonTerminal with a TerminalSymbol key and SymbolSequence value
     */
    private static Map<NonTerminalSymbol, Map<TerminalSymbol, SymbolSequence>> nonTerminalSymbolsMap = new HashMap<>();

    private static Map<TerminalSymbol, SymbolSequence> expressionMap = new HashMap<>();

    private static Map<TerminalSymbol, SymbolSequence> expression_tailMap = new HashMap<>();

    private static Map<TerminalSymbol, SymbolSequence> termMap = new HashMap<>();

    private static Map<TerminalSymbol, SymbolSequence> term_tailMap = new HashMap<>();

    private static Map<TerminalSymbol, SymbolSequence> unaryMap = new HashMap<>();

    private static Map<TerminalSymbol, SymbolSequence> factorMap = new HashMap<>();

    static {
        //Creates the list for expression
        SymbolSequence expression1 = SymbolSequence.build(TERM, EXPRESSION_TAIL);
        expressionMap.put(TerminalSymbol.VARIABLE, expression1);
        expressionMap.put(TerminalSymbol.OPEN, expression1);
        expressionMap.put(TerminalSymbol.MINUS, expression1);
        nonTerminalSymbolsMap.put(EXPRESSION, expressionMap);

        //Creates the list for expression_tail
        SymbolSequence expression_tail1 = SymbolSequence.build(TerminalSymbol.PLUS, TERM, EXPRESSION_TAIL);
        SymbolSequence expression_tail2 = SymbolSequence.build(TerminalSymbol.MINUS, TERM, EXPRESSION_TAIL);
        expression_tailMap.put(TerminalSymbol.PLUS, expression_tail1);
        expression_tailMap.put(TerminalSymbol.MINUS, expression_tail2);
        expression_tailMap.put(null, SymbolSequence.EPSILON);
        nonTerminalSymbolsMap.put(EXPRESSION_TAIL, expression_tailMap);

        //Creates the list for term
        SymbolSequence term1 = SymbolSequence.build(UNARY, TERM_TAIL);
        termMap.put(TerminalSymbol.MINUS, term1);
        termMap.put(TerminalSymbol.VARIABLE, term1);
        nonTerminalSymbolsMap.put(TERM, termMap);

        //Creates the list for term_list
        SymbolSequence term_tail1 = SymbolSequence.build(TerminalSymbol.TIMES, UNARY, TERM_TAIL);
        SymbolSequence term_tail2 = SymbolSequence.build(TerminalSymbol.DIVIDE, UNARY, TERM_TAIL);
        term_tailMap.put(TerminalSymbol.TIMES, term_tail1);
        term_tailMap.put(TerminalSymbol.DIVIDE, term_tail2);
        nonTerminalSymbolsMap.put(TERM_TAIL, term_tailMap);

        //creates the list for unary
        SymbolSequence unary1 = SymbolSequence.build(TerminalSymbol.MINUS, FACTOR);
        SymbolSequence unary2 = SymbolSequence.build(FACTOR);
        unaryMap.put(TerminalSymbol.MINUS, unary1);
        unaryMap.put(TerminalSymbol.VARIABLE, unary2);
        nonTerminalSymbolsMap.put(UNARY, unaryMap);

        //creates list for factor
        SymbolSequence factor1 = SymbolSequence.build(TerminalSymbol.OPEN, EXPRESSION, TerminalSymbol.CLOSE);
        SymbolSequence factor2 = SymbolSequence.build(TerminalSymbol.VARIABLE);
        factorMap.put(TerminalSymbol.OPEN, factor1);
        factorMap.put(TerminalSymbol.VARIABLE, factor2);
        nonTerminalSymbolsMap.put(FACTOR, factorMap);
    }

    public ParseState parse(List<Token> list) {
        SymbolSequence parseSequence = nonTerminalSymbolsMap.get(this).get(list.get(0).getType());
            ParseState p = parseSequence.match(Objects.requireNonNull(list,
                    "Input list is null, please enter a valid list"));
            if (p.isSuccess()) {
                return p;

        }
        return ParseState.FAILURE;
    }

    static final Optional<Node> parseInput (List<Token> input){
        Optional<Node> optionalNode = new Optional<>();
        ParseState p = EXPRESSION.parse(Objects.requireNonNull(input,
                "Input list is null, please enter a valid list"));
        if(p.isSuccess() && p.hasNoRemainder()) {
            return optionalNode.of(p.getNode());
        }
        else {
            return optionalNode.empty();
        }

    }
}

