//Make lists immutable (use collections)?
package Parser;

import java.util.*;

public final class InternalNode implements Node {

    private final List<Node> children;

    private Cache<InternalNode, String> cache;

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

    //return the concatenation of the children¡¯s lists
    public final List<Token> toList() {
        List<Token> output = new ArrayList<Token>();
        for (int i = 0; i < children.size(); i++) {
            output.addAll(children.get(i).toList());
        }
        return output;
    }

    //return a string representation of children
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int x = 0; x < children.size() - 1; x++) {
            builder.append(children.get(x).toString());
            builder.append(",");
        }

        builder.append(children.get(children.size() - 1).toString());
        builder.append("]");
        return builder.toString();
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

    public Optional<Node> firstChild() {
        if (this.isFruitful()) {
            return Optional.of(this.getChildren().get(0));
        } else {
            return Optional.empty();
        }
    }

    public boolean isSingleLeafParent() {
        return (this.getChildren().size() == 1 && this.getChildren().get(0).getChildren() == null);
    }

    public static class Builder {
        private List<Node> children = new ArrayList<>();

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

        private void simplifySingleLeafParents(){
            if(children.size() == 1){
                Node node = children.get(0);
                if(node.isSingleLeafParent()){
                    children.add(children.indexOf(node), node.getChildren().get(0));
                    children.remove(node);
                }
            }
        }


        public InternalNode build() {
            return InternalNode.build(children);
        }


    }

    //Main method for testing
    public static void main(String[] args) {
        Variable x = Variable.build("x");
        Variable y = Variable.build("y");
        Variable t = Variable.build("t");
        Connector plus = Connector.build(TerminalSymbol.PLUS);
        Connector divide = Connector.build(TerminalSymbol.DIVIDE);

        LeafNode leaf1 = LeafNode.build(x);
        LeafNode leaf2 = LeafNode.build(plus);
        LeafNode leaf3 = LeafNode.build(y);

        List<Node> list5 = new ArrayList<>();
        list5.add(leaf2);
        list5.add(leaf3);
        InternalNode internalNode5 = InternalNode.build(list5);

        List<Node> list6 = new ArrayList<>();
        list6.add(leaf1);
        list6.add(internalNode5);
        InternalNode internalNode6 = InternalNode.build(list6);

        Builder builder = new Builder();
        Builder builder1 = new Builder();
        builder1.addChild(internalNode6);
        System.out.println(builder1.children.toString());
        builder1.simplify();
        System.out.println(builder1.children.toString());
        InternalNode intNode = InternalNode.build(builder1.children);
        System.out.println(NonTerminalSymbol.EXPRESSION.parse(intNode.toList()).getRemainder().toString());
    }

}