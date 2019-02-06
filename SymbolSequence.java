package Parser;

import java.util.ArrayList;
import java.util.List;

final class SymbolSequence {

    private final List<Symbol> production;

    public static final SymbolSequence EPSILON = SymbolSequence.build(new ArrayList<Symbol>());

    private SymbolSequence(List<Symbol> production){
        this.production = production;
    }

    //Build method for SymbolSequence
    public  final static SymbolSequence build(List<Symbol> production){
        if(production == null)
            throw new NullPointerException("Null List");
        else
            return new SymbolSequence(production);
    }

    //Alternative build method, takes a variable number of args
    public final static SymbolSequence build(Symbol... symbols){
        List<Symbol> list = new ArrayList<>();
        if(symbols == null)
            throw new NullPointerException("No Symbols Found");
        else{
            for(Symbol symbol : symbols){
                list.add(symbol);
            }
            return new SymbolSequence(list);

        }
    }

    //Makes the production a string
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int x = 0; x < production.size()-1; x++) {
            builder.append(production.get(x).toString());
            builder.append(",");
        }

        builder.append(production.get(production.size()-1).toString());
        builder.append("]");
        return builder.toString();
    }

    //Returns a succesful ParseState if the parse of the remainder if succesful for all  of production
    public ParseState match(List<Token> input){
        if(input == null)
            throw new NullPointerException("Null Input");
        else {
            List<Token> remainder = input;
            List<Node> children = new ArrayList<>();
            for (Symbol symbol : production) {
                ParseState p = symbol.parse(remainder);
                if (p == ParseState.FAILURE)
                    return ParseState.FAILURE;
                else {
                    children.add(p.getNode());
                    remainder = p.getRemainder();
                }
            }
            InternalNode intNode = InternalNode.build(children);
            return ParseState.build(intNode, remainder);
        }
    }

}
