package Parser;

import java.util.*;

enum NonTerminalSymbol implements Symbol {

    //Non-Terminal types
    EXPRESSION, EXPRESSION_TAIL, TERM, TERM_TAIL, UNARY, FACTOR;

    Map<NonTerminalSymbol, List<SymbolSequence>> map = new HashMap<>();

    public ParseState parse(List<Token> list) {
        if(list == null)
            throw new NullPointerException("Null Input");
        return ParseState.build(null, null);
    }

    //makes a map of NonTerminals and a list of SymbolSequences
    private final void createMap(){
        SymbolSequence s1 = SymbolSequence.build(TERM, EXPRESSION_TAIL);
        SymbolSequence s2 = SymbolSequence.build(TerminalSymbol.PLUS, TERM, EXPRESSION_TAIL);
        SymbolSequence s3 = SymbolSequence.build(TerminalSymbol.MINUS, TERM, EXPRESSION_TAIL);
        SymbolSequence s4 =SymbolSequence.EPSILON;
        SymbolSequence s5 = SymbolSequence.build(UNARY, TERM_TAIL);
        SymbolSequence s6 = SymbolSequence.build(TerminalSymbol.TIMES, UNARY, TERM_TAIL);
        SymbolSequence s7 = SymbolSequence.build(TerminalSymbol.DIVIDE, UNARY, TERM_TAIL);
        SymbolSequence s8 = SymbolSequence.build(TerminalSymbol.MINUS, FACTOR);
        SymbolSequence s9 = SymbolSequence.build(FACTOR);
        SymbolSequence s10 = SymbolSequence.build(TerminalSymbol.VARIABLE);
        SymbolSequence s11 = SymbolSequence.build(EXPRESSION);

        List<SymbolSequence> l1 = new ArrayList<>();
        List<SymbolSequence> l2 = new ArrayList<>();
        List<SymbolSequence> l3 = new ArrayList<>();
        List<SymbolSequence> l4 = new ArrayList<>();
        List<SymbolSequence> l5 = new ArrayList<>();
        List<SymbolSequence> l6 = new ArrayList<>();

        l1.add(s1);
        l2.add(s2);
        l2.add(s3);
        l2.add(s4);
        l3.add(s5);
        l4.add(s6);
        l4.add(s7);
        l4.add(s4);
        l5.add(s8);
        l5.add(s9);
        l6.add(s10);
        l6.add(s11);

        map.put(EXPRESSION, l1);
        map.put(EXPRESSION_TAIL, l2);
        map.put(TERM,l3);
        map.put(TERM_TAIL, l4);
        map.put(UNARY, l5);
        map.put(FACTOR, l6);
    }



}
