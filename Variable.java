package Parser;
import java.util.*;
import java.util.function.Function;
public final class Variable extends AbstractToken{
  
  private final String representation;
  
  private static Cache<String, Variable> cache;
  
  //Returns the tpye as variable
  public TerminalSymbol getType(){
    return TerminalSymbol.VARIABLE; 
  }
  
  //Returns the string representation of the variable
  public final String getRepresentation(){
    return representation;
  }
  
  //Initializes the variable with its string representation 
  private Variable(String rep){
    this.representation = rep; 
    if(rep == null) {
      throw new NullPointerException("The parameter is null");
    }
  }
  
  //Builds the variable, catchs a null pointer exception if the argument is null
  public static final Variable build(String rep){
    try{ 
      Function<String, Variable> f = (s1) -> new Variable(s1);
      return cache.get(rep, f);
    }
    catch(NullPointerException E){
      System.out.println("Null pointer exception");
      return null;
    }
  }
  
  //Returns representation
  public String toString(){
    return representation;
  }
  
}