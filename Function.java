public class Function extends Type {

  public String name;
  public final Set<Type> higherSet;
  public final Set<Type> lowerSet;
  public final Set<Type> compatible;
  public final Set<TerminalSymbol> validConnectors;
  
  private Type(String name, Set<Type> higher, Set<Type> lower){
    this.name = name;
    this.higherSet = higher;
    this.lowerSet = lower;
    this.compatable = new HashSet<Type>(lower);
    this.compatable.addAll(higher);
    this.validConnectors = new HashSet<Type>().addAll(Stream.of(TerminalSymbol.PLUS, TerminalSymbol.TIMES).collect.Collectors.toSet()));
    //Set<Connector> tempSet = new HashSet<>();
    //tempSet.addAll(Stream.of(TerminalSymbol.PLUS, TerminalSymbol.TIMES).collect.Collectors.toSet()));
    //this.validConnectors = tempSet;
  }
  
  @override
  public String toString(){
    return  name;
    }
  
  public boolean isValidOperator(Connector c){
     return validConnectors.contains(c);
  }
  
  public Function Build(String name, Set<Type> higher, Set<Type> lower){
    return new Type(name, higher, lower); 
  }
  
}
