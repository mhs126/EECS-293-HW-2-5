public class Function extends Type {

  public String name;
  public Set<Type> higherSet;
  public Set<Type> lowerSet;
  public Set<Type> compatible;
  public Set<TerminalSymbol> validConnectors;
  
  private Type(String name){
    this.name = name;
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
  public static Function Build(String name){
    return new Function(name); 
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
