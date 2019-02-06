package Parser;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class LeafNodeTest {

    Variable x = Variable.build("x");
    LeafNode one = LeafNode.build(x);
    List<Token> list = new ArrayList<>();

    @Test
    public void testGetToken() {
        assertEquals(x, one.getToken());
    }

    @Test
    public void testToString() {
        assertEquals("x", one.toString());
    }

    @Test
    public void testToList() {
        list.add(x);
        assertEquals(list, one.toList());
    }
}