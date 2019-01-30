package parser;
import java.util.*;
import java.util.function.Function;
public final class Variable extends AbstractToken{
  
  private final String representation;
  
  private static Cache<String, Variable> cache;
  
  public TerminalSymbol getType(){
    return TerminalSymbol.VARIABLE; 
  }
  
  public final String getRepresentation(){
    return representation;
  }
  
  private Variable(String rep){
    this.representation = rep; 
    if(rep == null) {
      throw new NullPointerException("The parameter is null");
    }
  }
  
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
  
  public String toString(){
    return representation;
  }
  
}