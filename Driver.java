import java.util.*;

public class Driver {
    

    public static void main(String[] args) {
            //make an internal node
            Variable f = Variable.build("f");
            Variable g = Variable.build("g");
            Variable x = Variable.build("x");
            Variable y = Variable.build("y");
            Connector plus = Connector.build(TerminalSymbol.PLUS);
            Connector times = Connector.build(TerminalSymbol.TIMES);

            LeafNode leaf1 = LeafNode.build(f);
            LeafNode leaf2 = LeafNode.build(times);
            LeafNode leaf3 = LeafNode.build(g);
            LeafNode leaf4 = LeafNode.build(plus);
            LeafNode leaf5 = LeafNode.build(x);
            LeafNode leaf6 = LeafNode.build(plus);
            LeafNode leaf7 = LeafNode.build(y);

            List<Node> list = new ArrayList<>();
            list.add(leaf1);
            list.add(leaf2);
            list.add(leaf3);
            list.add(leaf4);
            list.add(leaf5);
            list.add(leaf6);
            list.add(leaf7);
            testNode = InternalNode.build(list);
            InternalNode internalNode = testNode;
            if (NonTerminalSymbol.EXPRESSION.parse(internalNode.toList()) == ParseState.FAILURE) {
                //die
                System.out.println("Invalid Expression");
                return;
            }

            //Map each variable(including functions)to the String representation of its Type
            HashMap<Variable, String> variableTypesString = new HashMap<>();
            variableTypesString.put(x, "int");
            variableTypesString.put(y, "double");
            variableTypesString.put(f, "function");
            variableTypesString.put(g, "function");

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

            //Map that links all variables (not functions) to their types
            //Collect unique types in hashset
            HashMap<Variable, Type> variableType = new HashMap<>();
            HashSet<Type> allTypes = new HashSet<>();

        //
        allTypes = createAllTypes(variableType, allTypes, variableTypesString);

        buildTypeSets(allTypes, lowerMap, higherMap);


        //User input for functions, map function to its input and output requirement
        HashMap<Variable, String> functionInputs = new HashMap<>();
        functionInputs.put(f, "double");
        functionInputs.put(g, "int");

        HashMap<Variable, String> functionOutputs = new HashMap<>();
        functionOutputs.put(f, "int");
        functionOutputs.put(g, "double");

        //build each function
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

        try {
            System.out.println(variableType);
            internalNode.evaluateTypes(variableType);
        }
        catch (IncompatibleTypeException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(internalNode.getCurrentType().toString());
    }

    //Creates the set of all types that aren't  functions
    public static HashSet<Type> createAllTypes(HashMap<Variable, Type> variableType,
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
        return allTypes;
    }

    //Builds the higher and lower sets of different types
    public static void buildTypeSets(HashSet<Type> allTypes,
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

