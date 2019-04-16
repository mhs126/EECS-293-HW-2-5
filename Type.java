public class Type {

  public String name;
  public final Set<Type> higherSet = new HashSet<>();
  public final Set<Type> lowerSet = new HashSet<>();
  public final Set<Type? compatable = new HashSet<>();
  public final Set<Connectors> validConnectors=  new HashSet<>();
  
  public Type(String name, Set<Type> higher, Set<Type> lower){
    this.name = name;
    this.higherSet = higher;
    this.lowerSet = lower;
    this.compatable = new HashSet<Type>(lower);
    this.compatable.addAll(higher);
    Set<Connectors> tempSet = new HashSet<>();
    tempSet.addAll(Connector.PLUS, Connector.MINUS, Connector.TIMES
                                Connector.DIVIDE, Connector.OPEN, Connector.CLOSE);
    this.validConnectors = tempSet;
  }
  
  @override
  public String toString(){
    return  name;
    }
  
  public boolean isValidOperator(Connector c){
     return validConnectors.contains(c);
  }
  
}
