//Make lists immutable (use collections)?
package Parser;

import com.sun.javafx.UnmodifiableArrayList;
import sun.util.BuddhistCalendar;

import java.util.*;

public final class InternalNode implements Node{

    private final List<Node> children;

    private Cache<InternalNode, String> cache;

    //a getter that returns a copy of the private children.
    public List<Node> getChildren(){
        return new ArrayList<>(children);
    }

    private InternalNode (List<Node> children){
        this.children = Collections.unmodifiableList(children);
    }

    //a build method returns a new internal node with the given children, or throws a NullPointerException if the argument is null.
    public final static InternalNode build(List<Node> children){
        return new InternalNode(Objects.requireNonNull(children,
                "Null input, please enter valid list"));
    }

    //return the concatenation of the children¡¯s lists
    public final List<Token> toList(){
        List<Token> output = new ArrayList<Token>();
        for (int i = 0; i < children.size(); i++){
            output.addAll(children.get(i).toList());
        }
        return output;
    }

    //return a string representation of children
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int x = 0; x < children.size()-1; x++) {
            builder.append(children.get(x).toString());
            builder.append(",");
        }

        builder.append(children.get(children.size()-1).toString());
        builder.append("]");
        return builder.toString();
    }

    public boolean isFruitful(){
        return (!children.isEmpty());
    }

    public boolean isOperator(){ return false;}

    public boolean isStartedByOperator(){
        return (this.isFruitful() && this.children.get(0).isOperator());
    }

    public Optional<Node> firstChild(){
        if(this.isFruitful()){
            return Optional.of(this.getChildren().get(0));
        }
        else{
            return Optional.empty();
        }
    }

    public boolean isSingleLeafParent(){
        return (this.getChildren().size() == 1 && this.getChildren().get(0).getChildren() == null);
    }

    public static class Builder {
        private List<Node> children = new ArrayList<>();

        public boolean addChild(Node node) {
            return Objects.requireNonNull(children.add(node),
                    "Null node input, please enter a valid node");
        }

        public Builder simplify(){
            this.children.removeIf(node -> !node.isFruitful());
            if(this.children.size() == 1 && this.children.get(0).getChildren() != null){
                List<Node> childList = new ArrayList<>(children.remove(0).getChildren());
                this.children.addAll(childList);
            }
            //Else we just return our current builder without adjusting the list
            return this;
        }

        public void simplifyFirstOperator(List<Node> list) {
            List<Node> tempList = new ArrayList<>(list);
                for (Node node : tempList) {
                    if (node.isStartedByOperator()) {
                        List<Node> childList = new ArrayList<>(node.getChildren());
                        children.addAll(1, childList);
                        children.remove(node);
                    }
                    if(node.getChildren() != null) {
                        this.simplifyFirstOperator(node.getChildren());
                    }
                }
            }
       public InternalNode build(){
            return InternalNode.build(children);
        }


    }

    //Main method for testing
    public static void main(String[] args){
        Variable x = Variable.build("x");
        Variable y = Variable.build("y");
        Variable t = Variable.build("t");
        Connector plus = Connector.build(TerminalSymbol.PLUS);
        Connector divide = Connector.build(TerminalSymbol.DIVIDE);

        LeafNode leaf1 = LeafNode.build(x);
        LeafNode leaf2 = LeafNode.build(plus);
        LeafNode leaf3 = LeafNode.build(y);
        LeafNode leaf4 = LeafNode.build(divide);
        LeafNode leaf5 = LeafNode.build(t);

        List<Node> list1 = new ArrayList<>();
        list1.add(leaf4);
        list1.add(leaf5);
        InternalNode internalNode1 = InternalNode.build(list1);

        List<Node> list2 = new ArrayList<>();
        list2.add(leaf3);
        list2.add(internalNode1);
        InternalNode internalNode2 = InternalNode.build(list2);

        List<Node> list3 = new ArrayList<>();
        list3.add(leaf2);
        list3.add(internalNode2);
        InternalNode internalNode3 = InternalNode.build(list3);

        List<Node> list4 = new ArrayList<>();
        list4.add(leaf1);
        list4.add(internalNode3);
        InternalNode internalNode4 = InternalNode.build(list4);

        Builder builder = new Builder();
        builder.addChild(internalNode4);
        System.out.println(builder.children.toString());
        builder.simplifyFirstOperator(builder.children);
        System.out.println(builder.children.toString());
    }


}