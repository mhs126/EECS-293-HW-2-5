import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public final boolean isSuccess(){
        return success;
    }

    public final Node getNode(){
        return node;
    }

    public final List<Token> getRemainder(){
        return new ArrayList<>(remainder);
    }

    //Returns true if remainder is null
    public final boolean hasNoRemainder(){
        return (remainder == null);
    }

    //build method for ParseState, throws a null pointer if any null input
    public final static ParseState build(Node node, List<Token> remainder){
        return new ParseState(true,
                Objects.requireNonNull(node, "Input node is null, please enter a valid node"),
                Objects.requireNonNull(remainder, "Input list is null, please enter a valid list"));
    }

}
