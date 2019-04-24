import com.sun.org.apache.bcel.internal.classfile.LocalVariableTable;

import java.util.*;

/**
 * Driver class that runs the typecheck program. Takes user input
 * using a static block that can be changed. uses HashSets and HashMaps
 * to compare the different Types of an InternalNode
 */

public class Driver {

    /**
     * InternalNode whose root Type is found
     */
    private static InternalNode internalNode;
    /**
     * HashMap that maps variables to their Type
     * Map is set in the static block
     */
    private static HashMap<Variable, Type> variableType = new HashMap<>();

    /**
     * Setter method for the InternalNode Driver runs on
     * Usually used for test cases
     * @param inputNode node internalNode is set to
     */
    public static void setInternalNode(InternalNode inputNode) {
        internalNode = inputNode;
    }

    /**
     * Static block that sets up the HashMaps and HashSets for
     * the type checking.
     */
    static {
        //make an internal node
        Variable f = Variable.build("f");
        Variable g = Variable.build("g");
        Variable x = Variable.build("x");
        Variable y = Variable.build("y");
        Variable a = Variable.build("a");
        Variable s = Variable.build("s");
        Connector plus = Connector.build(TerminalSymbol.PLUS);
        Connector times = Connector.build(TerminalSymbol.TIMES);

        LeafNode leaf1 = LeafNode.build(f);
        LeafNode leaf2 = LeafNode.build(times);
        LeafNode leaf3 = LeafNode.build(g);
        LeafNode leaf4 = LeafNode.build(plus);
        LeafNode leaf5 = LeafNode.build(x);
        LeafNode leaf6 = LeafNode.build(plus);
        LeafNode leaf7 = LeafNode.build(y);
        LeafNode leaf8 = LeafNode.build(a);
        LeafNode leaf9 = LeafNode.build(s);

        List<Node> list = new ArrayList<>();
        list.add(leaf1);
        list.add(leaf2);
        list.add(leaf3);
        list.add(leaf4);
        list.add(leaf5);
        list.add(leaf6);
        list.add(leaf7);
        list.add(leaf6);
        list.add(leaf8);
        internalNode = InternalNode.build(list);

        //Map each variable(including functions)to the String representation of its Type
        HashMap<Variable, String> variableTypesString = new HashMap<>();
        variableTypesString.put(x, "int");
        variableTypesString.put(y, "double");
        variableTypesString.put(f, "function");
        variableTypesString.put(g, "function");
        variableTypesString.put(a, "int");
        variableTypesString.put(s, "string");

        //Map each (String)Type to (String)hierarchies
        HashMap<String, Set<String>> lowerMap = new HashMap<>();
        HashMap<String, Set<String>> higherMap = new HashMap<>();
        HashSet<String> lowerSet = new HashSet<>();
        HashSet<String> higherSet = new HashSet<>();

        //populate sets and maps
        //int type maps
        lowerSet.add("int");
        lowerMap.put("int", new HashSet<>(lowerSet));
        higherSet.add("double");
        higherMap.put("int", new HashSet<>(higherSet));


        //double type maps
        lowerSet.add("double");
        lowerMap.put("double", new HashSet<>(lowerSet));
        higherSet.clear();
        higherMap.put("double", new HashSet<>(higherSet));

        //string type maps
        higherMap.put("string", new HashSet<>());
        lowerMap.put("string", new HashSet<>());

        //Map that links all variables (not functions) to their types
        //Collect unique types in hashset
        HashSet<Type> allTypes = new HashSet<>();

        //
        createAllTypes(variableType, allTypes, variableTypesString);
        buildTypeSets(allTypes, lowerMap, higherMap);


        //User input for functions, map function to its input and output requirement
        HashMap<Variable, String> functionInputs = new HashMap<>();
        functionInputs.put(f, "double");
        functionInputs.put(g, "int");

        HashMap<Variable, String> functionOutputs = new HashMap<>();
        functionOutputs.put(f, "int");
        functionOutputs.put(g, "double");

        for (Variable var:variableTypesString.keySet()) {
            if (variableTypesString.get(var).equals("function")) {
                String inputTypeString = functionInputs.get(var);
                String outputTypeString = functionOutputs.get(var);
                Type inputType = Type.build(inputTypeString);
                Type outputType = Type.build(outputTypeString);

                Function funcType = Function.build(var.toString(), inputType, outputType);
                variableType.put(var, funcType);
            }
        }
    }

    /**
     * Main method that runs the evaluateTypes method on internalNode field
     * @param args main method argument
     */
    public static void main(String[] args) {
        try {
            if (NonTerminalSymbol.EXPRESSION.parse(internalNode.toList()) == ParseState.FAILURE) {
                //die
                System.out.println("Invalid Expression");
                return;
            }
            internalNode.evaluateTypes(variableType);
            System.out.println(internalNode.getCurrentType().toString());
        }
        catch (IncompatibleTypeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates the set of allTypes that are not functions
     * @param variableType maps Variables to their Types
     * @param allTypes set of allTypes that is adjusted
     * @param variableTypesString maps the variables to the string representation of their Types
     */
    private static void createAllTypes(HashMap<Variable, Type> variableType,
                                       HashSet<Type> allTypes,
                                       HashMap<Variable, String> variableTypesString){
        //Build all variables (not functions), populate variableType and allTypes
        for (Variable var:variableTypesString.keySet()) {
            if (!variableTypesString.get(var).equals("function")) {
                Type varType = Type.build(variableTypesString.get(var));
                //connect the variable in user input to the Type that the user gave in the form of a string
                variableType.put(var, varType);
                //store all the types that the user gave into a hashset
                if (!allTypes.contains(varType)) {
                    allTypes.add(Type.build(variableTypesString.get(var)));
                }
                else {
                    // do nothing, variable type already made
                }
            }
        }
    }

    /**
     * Builds the higher and lower sets of different Types
     * @param allTypes set of all Types
     * @param lowerMap map used to build lower Type sets
     * @param higherMap map used to build higher Type sets
     */
    private static void buildTypeSets(HashSet<Type> allTypes,
                                      HashMap<String, Set<String>> lowerMap,
                                      HashMap<String, Set<String>> higherMap){
        //Build all type sets (not functions)
        for (Type type:allTypes) {
            //add yourself to lower
            type.addToLower(type);

            Set<String> typeHigherStrings = higherMap.get(type.toString());
            Set<String> typeLowerStrings = lowerMap.get(type.toString());
            for (String s: typeHigherStrings) {
                type.addToHigher(Type.build(s));
            }
            for (String s: typeLowerStrings) {
                type.addToLower(Type.build(s));
            }
        }

    }

}