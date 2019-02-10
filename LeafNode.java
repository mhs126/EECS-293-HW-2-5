package Parser;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        Objects.requireNonNull(token, "Input token is null, please input a valid token");
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

    //For testing
    public static void main(String[] args){
        Variable x = Variable.build("x");
        LeafNode l = LeafNode.build(x);
        System.out.println(l.toString());
    }

    public List<Node> getChildren(){
        return null;
    }

    public boolean isFruitful(){
        return true;
    }

}