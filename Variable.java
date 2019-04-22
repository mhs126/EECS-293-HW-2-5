import java.util.Objects;
import java.util.function.Function;
public final class Variable extends AbstractToken{

    private final String representation;

    private static Cache<String, Variable> cache = new Cache<String, Variable>();

    //Returns the type as variable
    public TerminalSymbol getType(){
        return TerminalSymbol.VARIABLE;
    }

    //Returns the string representation of the variable
    public final String getRepresentation(){
        return representation;
    }

    //Initializes the variable with its string representation
    private Variable(String rep){
        this.representation = Objects.requireNonNull(rep,
                "Input string is null, please enter a valid string");
        }

    //Builds the variable, throws a null pointer exception if the argument is null
    public static final Variable build(String rep){
        Objects.requireNonNull(rep, "Input string is null, please enter a valid string");
        return cache.get(rep, Variable::new);
    }

    public boolean isOperator(){
        return false;
    }

    //Returns representation
    public String toString(){
        return representation;
    }

}