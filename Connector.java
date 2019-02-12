//Make hash map instead of large switch statements
//Change constructor input to a TerminalSymbol
package Parser;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
public final class Connector extends AbstractToken{

    //A cache to check redundancy
    private static Cache<TerminalSymbol, Connector> cache = new Cache<TerminalSymbol, Connector>();

    //A map containing the connector terminalsymbol values that point to strings
    private static Map<TerminalSymbol, String> map = new HashMap<TerminalSymbol, String>();

    //A TerminalSymbol field
    private TerminalSymbol type;

    //Static block that adds values to the hashmap
    static {
        map.put(TerminalSymbol.PLUS, "+");
        map.put(TerminalSymbol.MINUS, "-");
        map.put(TerminalSymbol.DIVIDE, "/");
        map.put(TerminalSymbol.TIMES, "*");
        map.put(TerminalSymbol.OPEN, "(");
        map.put(TerminalSymbol.CLOSE, ")");
    }

    //Private Constructor that sets the type
    private Connector(TerminalSymbol t){
        type = Objects.requireNonNull(t, "Null TerminalSymbol input, please enter valid TerminalSymbol");
    }

    //Change to throw a null pointer
    public static final Connector build(TerminalSymbol t){
        Function<TerminalSymbol, Connector> f = (s) -> new Connector(s);
        return cache.get(Objects.requireNonNull(t,
                "Null TerminalSymbol input, please enter valid TerminalSymbol"), f);
    }

    //Returns type
    public TerminalSymbol getType(){
        return this.type;
    }

    //Returns the type as a string value
    public String toString(){
        return Objects.requireNonNull(map.get(type), "Key points to null, please input valid key");
    }

}

