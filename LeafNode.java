package Parser;
import java.util.*;
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
  public LeafNode buildLeafNode(Token token){
    if(token == null)
      throw new NullPointerException("The argument is null");
    
    LeafNode output = new LeafNode(token);
    return output;
  }
  
  public String toString(){
    return token.getType().toString();
  }
  
  //return a list containing as a single element its Token
  public final List<Token> toList(){
    List<Token> output = new ArrayList<Token>();
    output.add(token);
    return output;
  }
}