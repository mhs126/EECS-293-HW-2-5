package Parser;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class SymbolSequenceTest {

    SymbolSequence s = SymbolSequence.build(TerminalSymbol.PLUS, TerminalSymbol.MINUS, TerminalSymbol.DIVIDE);
    Variable x = Variable.build("x");
    Variable y = Variable.build("y");
    Variable t = Variable.build("t");
    Connector plus = Connector.build(TerminalSymbol.PLUS);
    LeafNode one = LeafNode.build(x);
    LeafNode two = LeafNode.build(y);
    LeafNode three = LeafNode.build(plus);
    LeafNode four = LeafNode.build(t);
    List<Token> list= new ArrayList<>();
    List<Node> list2 = new ArrayList<>();
    List<Token> empty = new ArrayList<>();

    @Test
    public void testToString() {
        assertEquals("[PLUS,MINUS,DIVIDE]", s.toString());
    }

    @Test
    public void testMatch() {
        list.add(x);
        list.add(t);
        list.add(y);
        list.add(plus);
        SymbolSequence s = SymbolSequence.build(TerminalSymbol.VARIABLE, TerminalSymbol.VARIABLE,TerminalSymbol.VARIABLE,TerminalSymbol.PLUS);
        list2.add(one);
        list2.add(four);
        list2.add(two);
        list2.add(three);
        InternalNode node = InternalNode.build(list2);
        assertEquals(true, s.match(list).getSuccess());

    }
}