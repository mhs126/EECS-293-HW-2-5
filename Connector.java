public final class Connector extends AbstractToken{
  
  private static Cache<String, Connector> cache;
  
  private String representation;
  
  private TerminalSymbol type;
    
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
  
  
  
}