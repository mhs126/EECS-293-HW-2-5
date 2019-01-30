package Parser;
import java.util.*;
public final class InternalNode implements Node{

  private final List<Node> children;
  
  //a getter that returns a copy of the private children.
  private List<Node> getChildren(){
    return children;
  }
  
  private InternalNode (List<Node> children){
    this.children = children;
  }
  
  //a build method returns a new internal node with the given children, or throws a NullPointerException if the argument is null.
  public InternalNode buildInternalNode(List<Node> children){
    if(children == null)
      throw new NullPointerException("The argument is null");
    
    InternalNode output = new InternalNode(children);
    return output;
  }
  
  //return the concatenation of the children¡¯s lists
  public final List<Token> toList(){
    int i = 0;
    List<Token> output = new ArrayList<Token>();
    while(!children.isEmpty()){
      output.add(children.get(i).toList().get(0));
      i++;
    }
    return output;
  }
  
  //return a string representation of children
  public String toString(){
    String output = "";
    for (Token token: this.toList())
      output = output + "[" + token.getType().toString()+ ",";
    
    for (int i = 0; i < this.toList().size(); i++)
      output = output + "]";
    
    return output;
  }
}