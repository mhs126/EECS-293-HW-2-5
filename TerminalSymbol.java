package Parser;

import java.util.List;

public enum TerminalSymbol implements Symbol{

    //The possible inputs to the tree
    VARIABLE, PLUS, MINUS, TIMES, DIVIDE, OPEN, CLOSE;

    public ParseState parse(List<Token> list){
        if(list.get(0).getType() == this){
            LeafNode leafNode = LeafNode.build(list.remove(0));
            return ParseState.build(leafNode, list);
        }
        else
            return ParseState.FAILURE;
    }

}