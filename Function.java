import java.util.Set;

/**
 * A class that extends Type. Possesses an input Type, output Type, higherSet, lowerSet, and compatible set
 * Function is a unique Type that behaves based on a given InputType and OutputType.
 * Viable arguments are of Type InputType, and the Function's inherent Type is it's OutputType
 */
public class Function extends Type {

    /**
     * String representation of the function
     */
    public String name;
    /**
     * required input Type of the function
     */
    private Type inputType;
    /**
     * output Type of the function
     */
    private Type outputType;
    private Set<Type> higherSet;
    private Set<Type> lowerSet;
    private Set<Type> compatible;

    /**
     * Cache to ensure no functions are declared twice
     */
    private static Cache<String, Function> cache = new Cache<>();

    /**
     * constructor that calls the constructor of the superclass
     * @param name name representation of the function
     */
    private Function(String name){
        super(name);
    }

    /**Public build method for Function
     *
     * @param name name representation of the Function
     * @param inputType input Type of the Function
     * @param outputType output Type of the Function
     * @return Function builds a new Function using the Cache
     */
    public static Function build(String name, Type inputType, Type outputType){
        Function thisFunc = cache.get(name,Function::new);
        thisFunc.outputType = outputType;
        thisFunc.inputType = inputType;
        thisFunc.higherSet = outputType.getHigherSet();
        thisFunc.lowerSet = outputType.getLowerSet();
        thisFunc.compatible = outputType.getCompatible();
        return thisFunc;
    }

    /**
     * checks if this Type is a function
     * @return boolean returns true
     */
    public boolean isFunction() {
        return true;
    }

    /**
     * getter method for the input Type
     * @return return the input Type of this Function
     */
    public Type getInputType() {
        return inputType;
    }

    /**
     * getter method for the output Type
     * @return return the output Type of this Function
     */
    public Type getOutputType() {
        return outputType;
    }
}