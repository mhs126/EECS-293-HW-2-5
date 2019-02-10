package Parser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

final class SymbolSequence {

    private final List<Symbol> production;

    public static final SymbolSequence EPSILON = SymbolSequence.build(new ArrayList<Symbol>());

    private SymbolSequence(List<Symbol> production){
        this.production = production;
    }

    //Build method for SymbolSequence
    public  final static SymbolSequence build(List<Symbol> production){
        Objects.requireNonNull(production, "Input list is null, please enter a valid list");
        return new SymbolSequence(production);
    }

    //Alternative build method, takes a variable number of args
    public final static SymbolSequence build(Symbol... symbols){
        Objects.requireNonNull(symbols, "Input is null, please enter valid symbol(s)");
        return new SymbolSequence(Arrays.asList(symbols));
    }

    //Makes the production a string
    public String toString(){
        return production.toString();
    }

    //Returns a successful ParseState if the parse of the remainder if succesful for all  of production
    public ParseState match(List<Token> input){
        Objects.requireNonNull(input, "Input list is null, please enter a valid list");
        List<Token> remainder = input;
        List<Node> children = new ArrayList<>();
        for (Symbol symbol : production) {
            ParseState p = symbol.parse(remainder);
            if (!p.isSuccess()) {
                return ParseState.FAILURE;
            }
            else {
                children.add(p.getNode());
                remainder = p.getRemainder();
            }
        }
        InternalNode intNode = InternalNode.build(children);
        return ParseState.build(intNode, remainder);
    }


}
