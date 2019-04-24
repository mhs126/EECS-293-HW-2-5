//Make lists immutable (use collections)?
import java.util.*;

public final class InternalNode implements Node {

    private final List<Node> children;
    private Type currentType;

    private Cache<InternalNode, String> cacheString = new Cache<>();
    private Cache<InternalNode, List<Token>> cacheList = new Cache<>();

    private int iterateCount = 1;

    //a getter that returns a copy of the private children.
    public List<Node> getChildren() {
        return new ArrayList<>(children);
    }

    private InternalNode(List<Node> children) {
        this.children = Collections.unmodifiableList(children);
    }

    //a build method returns a new internal node with the given children, or throws a NullPointerException if the argument is null.
    public final static InternalNode build(List<Node> children) {
        return new InternalNode(Objects.requireNonNull(children,
                "Null input, please enter valid list"));
    }

    //toList helper
    public final List<Token> buildList() {
        List<Token> output = new ArrayList<Token>();
        for (int i = 0; i < children.size(); i++) {
            output.addAll(children.get(i).toList());
        }
        return output;
    }

    //return the concatenation of the children¡¯s lists
    public final List<Token> toList() {
        return cacheList.get(this, InternalNode::buildList);
    }

    //toString Helper
    private final String buildString() {
        StringBuilder outString = new StringBuilder();
        outString.append("[");
        for (int i = 0; i < this.getChildren().size(); i++) {
            outString.append(this.getChildren().get(i).toString());
            if (i < this.getChildren().size() - 1) {
                outString.append(",");
            }
        }
        outString.append("]");
        return outString.toString();
    }

    // toString override
    @Override
    public String toString() {
        return cacheString.get(this, InternalNode::buildString);
    }

    public boolean isFruitful() {
        return (!children.isEmpty());
    }

    public boolean isOperator() {
        return false;
    }

    public boolean isStartedByOperator() {
        return (this.isFruitful() && this.children.get(0).isOperator());
    }

    public boolean isSingleLeafParent() {
        return (this.getChildren().size() == 1 && this.getChildren().get(0).getChildren() == null);
    }

    public Type getCurrentType() {
        return currentType;
    }


    public void evaluateTypes(HashMap<Variable,Type> variableTypes) throws IncompatibleTypeException {

        //iterate through children
        for (iterateCount = 0; iterateCount < children.size(); iterateCount++) {
            Node currentChild = children.get(iterateCount);
            if (currentChild.isOperator()) { //if node is an operator, do nothing
                //do nothing
            }
            else if (getLeafTypeFromVarTypes(currentChild,variableTypes).isFunction()) { //if node is a function, evaluate function

                Function thisFunction = (Function)getLeafTypeFromVarTypes(currentChild,variableTypes);
                try {
                    currentType = evaluateFunction(thisFunction, variableTypes);
                }
                catch (IncorrectOperatorException | IncorrectInputError e) {
                    System.out.println(e.getMessage());
                    break;
                }
            }
            else { //if node is a variable, calculate higher variable type
                if (iterateCount == 0) {
                    currentType = getLeafTypeFromVarTypes(children.get(0), variableTypes);
                }
                else {
                    currentType = calcHigher(currentType, getLeafTypeFromVarTypes(currentChild, variableTypes));
                }
            }
        }
    }


    private Type evaluateFunction(Function function, HashMap<Variable,Type> variableTypes) throws IncorrectOperatorException, IncorrectInputError {
        if (iterateCount + 1 >= children.size()) {
            throw new IncorrectInputError("Function has no argument");
        }

        Node operatorNode = children.get(iterateCount + 1);
        TerminalSymbol operator = operatorNode.toList().get(0).getType();
        Node nextFunctionNode = children.get(iterateCount + 2);

        if (operator == TerminalSymbol.PLUS) {
            //IncorrectInputError could be thrown here
            return evaluateApplyFunction(function, variableTypes, nextFunctionNode);
        }
        else if (operator == TerminalSymbol.TIMES) {
            //IncorrectInputError could be thrown here
            return evaluateAndThenFunction(function, variableTypes, nextFunctionNode);
        }
        else {
            throw new IncorrectOperatorException("- or / used after function call");
        }
    }

    private Type evaluateApplyFunction(Function function, HashMap<Variable,Type> variableTypes, Node argumentNode) throws IncorrectInputError{
        if (getLeafTypeFromVarTypes(argumentNode, variableTypes).isFunction()) {
            throw new IncorrectInputError("argument for Apply function is a function");
        }
        else {
            if (getLeafTypeFromVarTypes(argumentNode,variableTypes).equals(function.getInputType())) {
                iterateCount = iterateCount + 2;
                return function.getOutputType();
            }
            else {
                throw new IncorrectInputError("Incorrect function argument Type");
            }
        }
    }

    private Type evaluateAndThenFunction(Function function, HashMap<Variable,Type> variableTypes, Node argumentNode) throws IncorrectInputError, IncorrectOperatorException{
        if (getLeafTypeFromVarTypes(argumentNode, variableTypes).isFunction()) {
            iterateCount = iterateCount + 2;
            Function nextFunction = (Function)getLeafTypeFromVarTypes(argumentNode,variableTypes);
            if (evaluateFunction(nextFunction, variableTypes).equals(function.getInputType())) {
                return function.getOutputType();
            }
            else {
                throw new IncorrectInputError("Incorrect function argument Type");
            }
        }
        else {
            throw new IncorrectInputError("argument for AndThen function is not a function");
        }
    }


    //only call this when you know the leafNode is a variable, because it is sus
    private Type getLeafTypeFromVarTypes(Node child, HashMap<Variable,Type> variableTypes) {
        return variableTypes.get(child.toList().get(0));
    }

    private static Type calcHigher(Type first, Type second) throws IncompatibleTypeException {
        if (first.getHigherSet().contains(second)) {
            return second;
        }
        if (first.getLowerSet().contains(second)) {
            return first;
        }
        else {
            throw new IncompatibleTypeException("Type mismatch: " + first.toString() + " does not compare to " + second.toString());
        }
    }

    public static class Builder {
        private List<Node> children = new ArrayList<>();

        public List<Node> getChildren() {
            return children;
        }

        public boolean addChild(Node node) {
            return Objects.requireNonNull(children.add(node),
                    "Null node input, please enter a valid node");
        }

        public Builder simplify() {
            this.children.removeIf(node -> !node.isFruitful());
            if (this.children.size() == 1 && this.children.get(0).getChildren() != null) {
                List<Node> childList = new ArrayList<>(children.remove(0).getChildren());
                this.children.addAll(childList);
            }
            //Else we just return our current builder without adjusting the list

            List<Node> output = new ArrayList<>();
            for (Node nd : this.children) {
                output.add(this.simplifyHelper(nd));
            }
            this.children = output;
            this.simplifyFirstOperator();
            this.simplifySingleLeafParents();
            return this;
        }

        private Node simplifyHelper(Node node) {
            Builder builder = new Builder();
            if (node.getChildren() != null) {
                for (Node nd : node.getChildren()) {
                    builder.addChild(nd);
                }
                builder.simplify();
            }
            return node;
        }

        private void simplifyFirstOperator() {
            int index = 0;
            boolean isUnary = false;
            while (index < this.children.size()) {
                Node node = this.children.get(index);
                if (node.isStartedByOperator() && !isUnary) {
                    this.children.remove(node);
                    this.children.addAll(node.getChildren());
                }
                //otherwise the node is either not started by operator or isUnary, we don't want to make any change
                isUnary = this.children.get(index).isOperator();
                index++;
            }
        }

        private void simplifySingleLeafParents() {
            if (children.size() == 1) {
                Node node = children.get(0);
                if (node.isSingleLeafParent()) {
                    children.add(children.indexOf(node), node.getChildren().get(0));
                    children.remove(node);
                }
            }
        }

        public InternalNode build() {
            return InternalNode.build(children);
        }
    }

    class TestHook{
        Variable a = Variable.build("a");
        LeafNode n = LeafNode.build(a);
        List<Node> listNode = new ArrayList<>(Arrays.asList(n));
        Type type = Type.build("Dub");
        InternalNode t = InternalNode.build(listNode);

        public Type getTypeFromVarTypes(Node child,HashMap<Variable,Type> variableTypes){
            return t.getLeafTypeFromVarTypes(child, variableTypes);
        }

        public Type calcHigher(Type t, Type t2) throws IncompatibleTypeException{
            return InternalNode.calcHigher(t, t2);
        }
    }

}