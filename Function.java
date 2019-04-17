public class Function extends Type {

  public String name;
  public Set<Type> higherSet;
  public Set<Type> lowerSet;
  public Set<Type> compatible;
  public Set<TerminalSymbol> validConnectors;
  
  private Type(String name, Set<Type> higher, Set<Type> lower){
    this.name = name;
    this.higherSet = higher;
    this.lowerSet = lower;
    this.lowerSet.add(this)
    this.compatable = new HashSet<Type>(lower);
    this.compatable.addAll(higher);
    this.validConnectors = new HashSet<Type>().addAll(
                                              Stream.of(TerminalSymbol.PLUS, TerminalSymbol.TIMES)
                                              .collect
                                              .Collectors
                                              .toSet()));
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
  
  public void addToHigher(Type t){
    this.higherSet.add(t);
    this.compatible.add(t);
  }
  
  public void addToLower(Type t){
    this.lowerSet.add(t);
    this.compatible.add(t);
  }
  
}
