package Parser;

import java.util.*;

enum NonTerminalSymbol implements Symbol {

    //Non-Terminal types
    EXPRESSION, EXPRESSION_TAIL, TERM, TERM_TAIL, UNARY, FACTOR;

    //Creates the map with NonTerminalSymbol key and map value
    private static Map<NonTerminalSymbol, Map<TerminalSymbol, SymbolSequence>> nonTerminalSymbolsMap = new HashMap<>();


    static {
            /*
    Creates six maps, one for each NonTerminal with a TerminalSymbol key and SymbolSequence value
     */

        Map<TerminalSymbol, SymbolSequence> expressionMap = new HashMap<>();

        Map<TerminalSymbol, SymbolSequence> expression_tailMap = new HashMap<>();

        Map<TerminalSymbol, SymbolSequence> termMap = new HashMap<>();

        Map<TerminalSymbol, SymbolSequence> term_tailMap = new HashMap<>();

        Map<TerminalSymbol, SymbolSequence> unaryMap = new HashMap<>();

        Map<TerminalSymbol, SymbolSequence> factorMap = new HashMap<>();

        /*
        Creates the SymbolSequence for the expression map
        Adds the appropriate TerminalSymbols and expression to the expression map
        Adds the expression map to the nonTerminalSymbolsMap
         */
        SymbolSequence expression1 = SymbolSequence.build(TERM, EXPRESSION_TAIL);
        expressionMap.put(TerminalSymbol.VARIABLE, expression1);
        expressionMap.put(TerminalSymbol.OPEN, expression1);
        expressionMap.put(TerminalSymbol.MINUS, expression1);
        nonTerminalSymbolsMap.put(EXPRESSION, expressionMap);

        /*
        Creates the SymbolSequences for the expression_tail map
        Adds the appropriate TerminalSymbols and expression_tail to the expression_tail map
        Adds the expression_tail map to the nonTerminalSymbolsMap
         */
        SymbolSequence expression_tail1 = SymbolSequence.build(TerminalSymbol.PLUS, TERM, EXPRESSION_TAIL);
        SymbolSequence expression_tail2 = SymbolSequence.build(TerminalSymbol.MINUS, TERM, EXPRESSION_TAIL);
        expression_tailMap.put(TerminalSymbol.PLUS, expression_tail1);
        expression_tailMap.put(TerminalSymbol.MINUS, expression_tail2);
        expression_tailMap.put(TerminalSymbol.CLOSE, SymbolSequence.EPSILON);
        expression_tailMap.put(null, SymbolSequence.EPSILON);
        nonTerminalSymbolsMap.put(EXPRESSION_TAIL, expression_tailMap);

        /*
        Creates the SymbolSequence for the term map
        Adds the appropriate TerminalSymbols and term to the term map
        Adds the term map to the nonTerminalSymbolsMap
         */
        SymbolSequence term1 = SymbolSequence.build(UNARY, TERM_TAIL);
        termMap.put(TerminalSymbol.MINUS, term1);
        termMap.put(TerminalSymbol.OPEN, term1);
        termMap.put(TerminalSymbol.VARIABLE, term1);
        nonTerminalSymbolsMap.put(TERM, termMap);

        /*
        Creates the SymbolSequences for the term_tail map
        Adds the appropriate TerminalSymbols and term_tail to the term_tail map
        Adds the term_tail map to the nonTerminalSymbolsMap
         */
        SymbolSequence term_tail1 = SymbolSequence.build(TerminalSymbol.TIMES, UNARY, TERM_TAIL);
        SymbolSequence term_tail2 = SymbolSequence.build(TerminalSymbol.DIVIDE, UNARY, TERM_TAIL);
        term_tailMap.put(TerminalSymbol.TIMES, term_tail1);
        term_tailMap.put(TerminalSymbol.DIVIDE, term_tail2);
        term_tailMap.put(TerminalSymbol.CLOSE, SymbolSequence.EPSILON);
        term_tailMap.put(TerminalSymbol.MINUS, SymbolSequence.EPSILON);
        term_tailMap.put(TerminalSymbol.PLUS, SymbolSequence.EPSILON);
        term_tailMap.put(null, SymbolSequence.EPSILON);
        nonTerminalSymbolsMap.put(TERM_TAIL, term_tailMap);

        /*
        Creates the SymbolSequences for the expression map
        Adds the appropriate TerminalSymbols and unary to the unary map
        Adds the unary map to the nonTerminalSymbolsMap
         */
        SymbolSequence unary1 = SymbolSequence.build(TerminalSymbol.MINUS, FACTOR);
        SymbolSequence unary2 = SymbolSequence.build(FACTOR);
        unaryMap.put(TerminalSymbol.MINUS, unary1);
        unaryMap.put(TerminalSymbol.OPEN, unary2);
        unaryMap.put(TerminalSymbol.VARIABLE, unary2);
        nonTerminalSymbolsMap.put(UNARY, unaryMap);

        /*
        Creates the SymbolSequences for the expression map
        Adds the appropriate TerminalSymbols and factor to the factor map
        Adds the factor map to the nonTerminalSymbolsMap
         */
        SymbolSequence factor1 = SymbolSequence.build(TerminalSymbol.OPEN, EXPRESSION, TerminalSymbol.CLOSE);
        SymbolSequence factor2 = SymbolSequence.build(TerminalSymbol.VARIABLE);
        factorMap.put(TerminalSymbol.OPEN, factor1);
        factorMap.put(TerminalSymbol.VARIABLE, factor2);
        nonTerminalSymbolsMap.put(FACTOR, factorMap);
    }

    /*
    Takes a List of Tokens as input and returns a ParseState
    Returns a successful ParseState if the current list of tokens matches the SymbolSequence from the map
    Returns a failed ParseState if it does not match
     */
    public ParseState parse(List<Token> list) {
        Objects.requireNonNull(list, "Input is null, please enter a valid list");
        //Check for empty list and use a null key
        if(list.isEmpty()){
            return nonTerminalSymbolsMap.get(this).get(null).match(list);
        }
        else {
            SymbolSequence parseSequence = nonTerminalSymbolsMap.get(this).get(list.get(0).getType());
            ParseState p = parseSequence.match(list);
            //return the match
            if (p.isSuccess()) {
                return p;

            }
            return ParseState.FAILURE;
        }
    }

    //Call optional.empty and of
    static final Optional<Node> parseInput (List<Token> input){
        ParseState p = EXPRESSION.parse(Objects.requireNonNull(input,
                "Input list is null, please enter a valid list"));
        if(p.isSuccess() && p.hasNoRemainder()) {
            return Optional.of(p.getNode());
        }
        else {
            return Optional.empty();
        }

    }

    public static void main(String[] args){
        Variable a = Variable.build("a");
        Variable b = Variable.build("b");
        Variable c = Variable.build("c");
        Connector plus = Connector.build(TerminalSymbol.PLUS);
        Connector divide = Connector.build(TerminalSymbol.DIVIDE);
        List<Token> list = new ArrayList<>();
        list.add(a);
        list.add(plus);
        list.add(b);
        list.add(divide);
        list.add(c);
        System.out.println( EXPRESSION.parse(list).getRemainder().toString());
        System.out.println(nonTerminalSymbolsMap.get(EXPRESSION).get(TerminalSymbol.VARIABLE).toString());
    }

}

