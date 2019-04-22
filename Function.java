import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Function extends Type {

    public String name;
    public Type inputType;
    public Type outputType;
    public Set<Type> higherSet;
    public Set<Type> lowerSet;
    public Set<Type> compatible;
    public Set<TerminalSymbol> validConnectors = new HashSet<>(Arrays.asList(TerminalSymbol.PLUS, TerminalSymbol.MINUS));

    private static Cache<String, Function> cache = new Cache<>();

    private Function(String name){
        super(name);
        this.name = name;
    }

    /*
    INPUT: TerminalSymbol
    returns true if the input is in the validConnectors set
    */
    public boolean isValidOperator(TerminalSymbol c){
        return validConnectors.contains(c);
    }

    public boolean isValidInput(Type input) {
        return input == inputType;
    }

    //Public build method for Function
    public static Function build(String name, Type inputType, Type outputType){
        Function thisFunc = cache.get(name,Function::new);
        thisFunc.outputType = outputType;
        thisFunc.inputType = inputType;
        thisFunc.higherSet = outputType.higherSet;
        thisFunc.lowerSet = outputType.lowerSet;
        thisFunc.compatible = outputType.compatible;
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

    @Override
    public boolean equals(Object input) {
        Function inputType = (Function) input;
        return this.name.equals(inputType.name);
    }
}