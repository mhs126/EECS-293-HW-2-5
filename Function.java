import java.util.Set;

public class Function extends Type {

    public String name;
    private Type inputType;
    private Type outputType;
    private Set<Type> higherSet;
    private Set<Type> lowerSet;
    private Set<Type> compatible;

    private static Cache<String, Function> cache = new Cache<>();

    private Function(String name){
        super(name);
    }

    //Public build method for Function
    public static Function build(String name, Type inputType, Type outputType){
        Function thisFunc = cache.get(name,Function::new);
        thisFunc.outputType = outputType;
        thisFunc.inputType = inputType;
        thisFunc.higherSet = outputType.getHigherSet();
        thisFunc.lowerSet = outputType.getLowerSet();
        thisFunc.compatible = outputType.getCompatible();
        return thisFunc;
    }

    public boolean isFunction() {
        return true;
    }

    public Type getInputType() {
        return inputType;
    }

    public Type getOutputType() {
        return outputType;
    }
}