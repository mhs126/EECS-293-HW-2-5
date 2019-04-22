import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Type {

    public String name;
    public Set<Type> higherSet = new HashSet<>();
    public Set<Type> lowerSet = new HashSet<>();
    public Set<Type> compatible = new HashSet<>();
    //public final Set<TerminalSymbol> validConnectors;

    private static Cache<String, Type> cache = new Cache<>();

    Type(String name){
        this.name = name;
        //this.higherSet = higher;
        //this.lowerSet = lower;
        //this.compatible = new HashSet<>(lower);
        //this.compatible.addAll(higher);

        /*
        Set<TerminalSymbol> tempSet = new HashSet<>();
        tempSet.addAll(Stream.of(TerminalSymbol.PLUS, TerminalSymbol.MINUS, TerminalSymbol.TIMES,
                TerminalSymbol.DIVIDE, TerminalSymbol.OPEN, TerminalSymbol.CLOSE).collect(Collectors.toSet()));
        this.validConnectors = tempSet;
        */
    }

    @Override
    public String toString(){
        return name;
    }

    public static Type build(String name) {
        return cache.get(name, Type::new);
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

    /*
    public boolean isValidOperator(TerminalSymbol c){
        return validConnectors.contains(c);
    }
    */

    public boolean isFunction() {
        return false;
    }

    @Override
    public boolean equals(Object input) {
        Type inputType = (Type)input;
        return this.name.equals(inputType.name);
    }
}
