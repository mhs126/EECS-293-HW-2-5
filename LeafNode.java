package Parser;
import java.util.ArrayList;
import java.util.List;
public final class LeafNode implements Node{

    private final Token token;

    //private constructor that sets the Token value
    private LeafNode(Token token){
        this.token = token;
    }

    public Token getToken(){
        return token;
    }

    //a build method that returns a new leaf with the given token,or throws a NullPointerException if the argument is null.
    public final static LeafNode build(Token token){
        if(token == null)
            throw new NullPointerException("The argument is null");

        return new LeafNode(token);
    }

    public String toString(){
        return token.toString();
    }

    //return a list containing as a single element its Token
    public final List<Token> toList(){
        List<Token> output = new ArrayList<Token>();
        output.add(token);
        return output;
    }
}