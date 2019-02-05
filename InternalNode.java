//Make lists immutable (use collections)?
package Parser;

import java.util.ArrayList;
import java.util.List;

public final class InternalNode implements Node{

    private final List<Node> children;

    //a getter that returns a copy of the private children.
    private List<Node> getChildren(){
        return children;
    }

    private InternalNode (List<Node> children){
        this.children = new ArrayList<>(children);
    }

    //a build method returns a new internal node with the given children, or throws a NullPointerException if the argument is null.
    public final static InternalNode build(List<Node> children){
        if(children == null)
            throw new NullPointerException("The argument is null");

        return new InternalNode(children);
    }

    //return the concatenation of the children¡¯s lists
    public final List<Token> toList(){
        //int i = 0;
        List<Token> output = new ArrayList<Token>();
        for (int i = 0; i < children.size(); i++){
            output.addAll(children.get(i).toList());
            //i++;
        }
        return output;
    }

    //return a string representation of children
    public String toString(){
        String output = "";
        for (Token token: this.toList())
            output = output + "[" + token.toString()+ ",";

        for (int i = 0; i < this.toList().size(); i++)
            output = output + "]";

        return output;
    }

    public static void main(String[] args){
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
    }


}