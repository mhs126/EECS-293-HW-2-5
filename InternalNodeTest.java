import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class InternalNodeTest {


    InternalNode intNode;
    Type t = Type.build("Dub");
    Type t2 = Type.build("int");
    Type t3 = Type.build("String");
    Map<Variable, Type> varTypes = new HashMap<>();
    static Variable g = Variable.build("g");
    static LeafNode leafNodeS = LeafNode.build(g);
    static List<Node> listNodeS = new ArrayList<>(Arrays.asList(leafNodeS));
    private static final InternalNode.TestHook hook = InternalNode.build(listNodeS).new TestHook();


    @Before
    public void setUp(){
        Variable a = Variable.build("a");
        Variable b = Variable.build("b");
        Variable c = Variable.build("c");
        Connector plus = Connector.build(TerminalSymbol.PLUS);
        Connector divide = Connector.build(TerminalSymbol.DIVIDE);
        LeafNode leafNode = LeafNode.build(a);
        LeafNode leafNode2 = LeafNode.build(b);
        LeafNode leafNode3 = LeafNode.build(c);
        LeafNode leafNode4 = LeafNode.build(plus);
        LeafNode leafNode5 = LeafNode.build(divide);
        List<Node> listNode = new ArrayList<>(Arrays.asList(leafNode, leafNode2, leafNode3, leafNode4, leafNode5));
        intNode = InternalNode.build(listNode);
        varTypes.put(a, t);
        varTypes.put(b, t2);
        t.addToLower(t2);
        t2.addToHigher(t);
    }

    @Test
    public void get_Current_Type_Nominal() {
        assertNull(intNode.getCurrentType());
    }

    @Test
    public void get_Current_Type_Real_Type() {
        assertNull(intNode.getCurrentType());
    }

    @Test
    public void setCurrentType() {
        intNode.setCurrentType(t);
        assertEquals(t, intNode.getCurrentType());
    }

    @Test
    public void evaluateTypes() {
    }

    @Test
    public void calc_Higher_first() throws IncompatibleTypeException{
        assertEquals(t, hook.calcHigher(t, t2));
    }

    @Test
    public void calc_Higher_second() throws IncompatibleTypeException{
        assertEquals(t, hook.calcHigher(t2, t));
    }

    @Test (expected = IncompatibleTypeException.class)
    public void calc_Higher_exception() throws IncompatibleTypeException{
        assertEquals(t, hook.calcHigher(t2, t3));
    }
}