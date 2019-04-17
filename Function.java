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
  
  //Overrides the toString method
  //Returns the name of the type
  @Override
  public String toString(){
    return  name;
    }
  /*
  INPUT: TerminalSymbol 
  returns true if the input is in the validConnectors set
  */
  public boolean isValidOperator(TerminalSymbol c){
     return validConnectors.contains(c);
  }
  
  //Public build method for Function
  public Function Build(String name, Set<Type> higher, Set<Type> lower){
    return new Type(name, higher, lower); 
  }
  
  /*
  INPUT: Type
  adds the input Type to the higher and compatible sets
  */
  public void addToHigher(Type t){
    this.higherSet.add(t);
    this.compatible.add(t);
  }
  
    /*
  INPUT: Type
  adds the input Type to the lower and compatible sets
  */
  public void addToLower(Type t){
    this.lowerSet.add(t);
    this.compatible.add(t);
  }
  
}
