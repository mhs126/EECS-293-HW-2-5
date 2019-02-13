//Make lists immutable (use collections)?
package Parser;

import com.sun.javafx.UnmodifiableArrayList;
import sun.util.BuddhistCalendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    public static class Builder {
        private List<Node> children = new ArrayList<>();

        public boolean addChild(Node node) {
            return Objects.requireNonNull(this.children.add(node),
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


       public InternalNode build(){
            return new InternalNode(children);
        }



    }

    //Main method for testing
    /*public static void main(String[] args){
        Variable x = Variable.build("x");
        Variable y = Variable.build("y");
        Variable t = Variable.build("t");
        Connector plus = Connector.build(TerminalSymbol.PLUS);
        System.out.println(plus.toString());
        LeafNode one = LeafNode.build(x);
        LeafNode two = LeafNode.build(y);
        LeafNode three = LeafNode.build(plus);
        LeafNode four = LeafNode.build(t);
        List<Node> nodeList = new ArrayList<Node>();
        List<Node> nodeList2 = new ArrayList<Node>();
        nodeList.add(one);
        nodeList.add(four);
        nodeList2.add(three);
        nodeList2.add(two);
        InternalNode intNode2 = InternalNode.build(nodeList2);
        nodeList.add(intNode2);
        InternalNode intNode = InternalNode.build(nodeList);
        System.out.println(intNode.toString());
    } */


}