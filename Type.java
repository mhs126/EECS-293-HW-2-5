public class Type {

  public String name;
  public final List<Type> higherList = new ArrayList<>();
  public final List<Type> lowerList = new ArrayList<>();
  public final List<Type? compatable = new ArrayList<>();
  public final List<Connectors> validConnectors=  new ArrayList<>();
  
  public Type(String name, List<String> higher, List<String> lower){
    this.name = name;
    this.higher = higherList;
    this.lower = lowerList;
    this.compatable = new ArrayList<Type>(lowerList);
    this.compatable.addAll(higherList);
    List<Connectors> tempList = new ArrayList<>();
    tempList.addAll(Connector.PLUS, Connector.MINUS, Connector.TIMES
                                Connector.DIVIDE, Connector.OPEN, Connector.CLOSE);
    this.validConnectors = tempList;
  }
  
  @override
  public String toString(){
    return  name;
    }
}
