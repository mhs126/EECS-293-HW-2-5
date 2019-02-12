//Make lists immutable (use collections)?
package Parser;

import sun.util.BuddhistCalendar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class InternalNode implements Node{

    private final List<Node> children;

    //a getter that returns a copy of the private children.
    public List<Node> getChildren(){
        return new ArrayList<>(children);
    }

    private InternalNode (List<Node> children){
        this.children = new ArrayList<>(children);
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

    public class Builder {
        private List<Node> children;

        public boolean addChild(Node node) {
            return children.add(node);
        }

        public Builder simplify(){
            List<Node> simplifiedList = this.removeChildlessNodes();
            Builder builder = new Builder();
            if(simplifiedList.size() == 1 && simplifiedList.get(0).getChildren() != null){
                builder.setChildren(simplifiedList.get(0).getChildren());
            }
            else{
                builder.setChildren(simplifiedList);
            }
            return builder;
        }



        /*
        Helper method for simplify
        Removes all nodes from children that have no children
        Makes a copy of the list
         */
        private List<Node> removeChildlessNodes() {
            List<Node> returnList = new ArrayList<>(children);
            for (Node node : returnList) {
                if (!node.isFruitful()) {
                    returnList.remove(node);
                }
                //else we keep the node
            }
            return returnList;
        }

        /*
            Setter method for children
            Helper method for simplify
        */
        private void setChildren(List<Node> list){
            this.children = list;
        }

       /*public InternalNode build(){
            InternalNode (SymbolSequence :: match);
        }*/



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