//Make hash map instead of large switch statements
//Change constructor input to a TerminalSymbol
package Parser;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
public final class Connector extends AbstractToken{

    //A cache to check redundancy
    private static Cache<TerminalSymbol, Connector> cache = new Cache<TerminalSymbol, Connector>();

    //A map containing the connector terminalsymbol values that point to strings
    private Map<TerminalSymbol, String> map = new HashMap<TerminalSymbol, String>();

    //A TerminalSymbol field
    private TerminalSymbol type;

    //Private Constructor that sets the type
    private Connector(TerminalSymbol t){
        map.put(TerminalSymbol.PLUS, "+");
        map.put(TerminalSymbol.MINUS, "-");
        map.put(TerminalSymbol.DIVIDE, "/");
        map.put(TerminalSymbol.TIMES, "*");
        map.put(TerminalSymbol.OPEN, "(");
        map.put(TerminalSymbol.CLOSE, ")");
        if(map.get(t) == null)
            throw new NullPointerException("Invalid input");
        else
            type = t;
    }

    //Change to throw a null pointer
    public static final Connector build(TerminalSymbol t){
        if(t == null)
            throw new NullPointerException("No TerminalSymbol found");
        else{
            Function<TerminalSymbol, Connector> f = (s) -> new Connector(s);
            return cache.get(t, f);
        }
    }

    //Returns type
    public TerminalSymbol getType(){
        return this.type;
    }

    //Returns the type as a string value
    public String toString(){
        if(map.get(type) != null)
            return map.get(type);
        else
            throw new NullPointerException("Null Input");
    }

}

