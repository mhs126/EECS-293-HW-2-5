import java.util.function.Function;
public final class Connector extends AbstractToken{
  
  //A cache to check redundency
  private static Cache<String, Connector> cache;
  
  //A String field
  private String representation;
  
  //A TerminalSymbol field
  private TerminalSymbol type;
  
  //Private Constructor that sets the type
  private Connector(String rep){
    this.representation = rep;
    switch(rep){
      case "+":
        type = TerminalSymbol.PLUS;
        break;
      case "-":
        type = TerminalSymbol.MINUS;
        break;
      case "*":
        type = TerminalSymbol.TIMES;
        break;
      case "/":
        type = TerminalSymbol.DIVIDE;
        break;
      case "(":
        type = TerminalSymbol.OPEN;
        break;
      case ")":
        type = TerminalSymbol.CLOSE;
        break;
    }
  }
  
  //Not sure what it does exactly, will go over this later
  public static final Connector build(String rep){
    try{ 
      Function<String, Connector> f = (s1) -> new Connector(s1);
      return cache.get(rep, f);
    }
    catch(NullPointerException E){
      System.out.println("Null pointer exception");
      return null;
    }
  }
  
  //Returns type
  public TerminalSymbol getType(){
    return type; 
  }
  
  //Returns the type as a string value
  public String toString(){
    switch(this.getType()){
      case PLUS:
        return "+";
      case MINUS:
        return "-";
      case TIMES:
        return "*";
      case DIVIDE:
        return "/";
      case OPEN:
        return "(";
      case CLOSE:
        return ")";
    }
    throw new NullPointerException("Null Input");
  }
  
}  

