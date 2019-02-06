package Parser;

import java.util.ArrayList;
import java.util.List;

public final class ParseState {

    private final boolean success;

    private final Node node;

    private final List<Token> remainder;

    final static ParseState FAILURE = new ParseState(false, null, null);

    //Constructor for ParseState, sets the field variables
    private ParseState(boolean success, Node node, List<Token> remainder){
        this.success = success;
        this.node = node;
        this.remainder = remainder;
    }

    public final boolean getSuccess(){
        return success;
    }

    public final Node getNode(){
        return node;
    }

    public final List<Token> getRemainder(){
        return remainder;
    }

    //Returns true if remainder is null
    public final boolean hasNoRemainder(){
        if(remainder == null)
            return true;
        else
            return false;
    }

    //build method for ParseState, throws a null pointer if any null input
    public final static ParseState build(Node node, List<Token> remainder){
        if(node == null || remainder == null)
            throw new NullPointerException("Null Argument Given");
        else
            return new ParseState(true, node, remainder);
    }

}
