package Parser;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class InternalNodeTest {

    Variable x = Variable.build("x");
    Variable y = Variable.build("y");
    Variable t = Variable.build("t");
    Connector plus = Connector.build(TerminalSymbol.PLUS);
    LeafNode one = LeafNode.build(x);
    LeafNode two = LeafNode.build(y);
    LeafNode three = LeafNode.build(plus);
    LeafNode four = LeafNode.build(t);
    List<Node> nodeList = new ArrayList<Node>();
    List<Node> nodeList2 = new ArrayList<Node>();


    @org.testng.annotations.Test
    public void testBuild() {
    }

    @org.testng.annotations.Test
    public void testToList() {
        nodeList.add(one);
        nodeList.add(two);
        InternalNode intNode = InternalNode.build(nodeList);
        for (int x = 0; x < nodeList.size(); x++)
            assertEquals(nodeList.get(x).toString(), intNode.toList().get(x).toString());
    }

    @org.testng.annotations.Test
    public void testToString() {
        nodeList.add(one);
        nodeList.add(two);
        InternalNode intNode = InternalNode.build(nodeList);
        assertEquals("[x,y]", intNode.toString());
    }
}
