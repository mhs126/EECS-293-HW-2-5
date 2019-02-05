package Parser;
import java.util.function.Function;
public final class Variable extends AbstractToken{

    private final String representation;

    private static Cache<String, Variable> cache = new Cache<String, Variable>();

    //Returns the tpye as variable
    public TerminalSymbol getType(){
        return TerminalSymbol.VARIABLE;
    }

    //Returns the string representation of the variable
    public final String getRepresentation(){
        return representation;
    }

    //Initializes the variable with its string representation
    private Variable(String rep){
        if(rep == null) {
            throw new NullPointerException("The parameter is null");
        }
        else{
            this.representation = rep;
        }
    }

    //Builds the variable, throws a null pointer exception if the argument is null
    public static final Variable build(String rep){
        if(rep == null){
            throw new NullPointerException("Null input");
        }
        else{
            Function<String, Variable> f = (s) -> new Variable(s);
            return cache.get(rep, f);
        }
    }

    //Returns representation
    public String toString(){
        return representation;
    }

}