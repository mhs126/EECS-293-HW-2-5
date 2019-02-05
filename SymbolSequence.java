package Parser;

import java.util.ArrayList;
import java.util.List;

final class SymbolSequence {

    private final List<Symbol> production;

    public static final SymbolSequence EPSILON = SymbolSequence.build(new ArrayList<Symbol>());

    private SymbolSequence(List<Symbol> production){
        this.production = production;
    }

    private  final static SymbolSequence build(List<Symbol> production){
        if(production == null)
            throw new NullPointerException("Null List");
        else
            return new SymbolSequence(production);
    }

    private final static SymbolSequence build(Symbol... symbols){
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

    public String toString(){
        String output = "";
        for (Symbol symbol: production)
            output = output + "[" + symbol.toString()+ ",";

        for (int i = 0; i < production.size(); i++)
            output = output + "]";

        return output;
    }

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
