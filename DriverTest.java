import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DriverTest {

    private InternalNode singleLeaf;

    private InternalNode incorrectOperator;

    private InternalNode badFunctionInput;

    private InternalNode applyFunctionToFunction;

    private InternalNode applyFunctionToVariable;

    private InternalNode andThenFunctionToFunction;

    private InternalNode andThenFunctionIncompatibleTypes;

    private InternalNode andThenFunctionToVariable;

    private InternalNode sameType;

    private InternalNode incompatibleTypes;

    InternalNode emptyNode = InternalNode.build(new ArrayList<>());

    @Before
    public void setUp(){
        Variable f = Variable.build("f");
        Variable g = Variable.build("g");
        Variable x = Variable.build("x");
        Variable y = Variable.build("y");
        Connector plus = Connector.build(TerminalSymbol.PLUS);
        Connector times = Connector.build(TerminalSymbol.TIMES);
        Connector minus = Connector.build(TerminalSymbol.MINUS);
        Variable s = Variable.build("s");

        LeafNode leaf1 = LeafNode.build(f);
        LeafNode leaf2 = LeafNode.build(times);
        LeafNode leaf3 = LeafNode.build(g);
        LeafNode leaf4 = LeafNode.build(plus);
        LeafNode leaf5 = LeafNode.build(x);
        LeafNode leaf6 = LeafNode.build(plus);
        LeafNode leaf7 = LeafNode.build(y);
        LeafNode leaf8 = LeafNode.build(s);
        LeafNode leaf10 = LeafNode.build(minus);

        List<Node> list = new ArrayList<>();
        list.add(leaf5);
        list.add(leaf2);
        list.add(leaf8);
        incompatibleTypes = InternalNode.build(list);

        List<Node> list1 = new ArrayList<>();
        list1.add(leaf1);
        list1.add(leaf10);
        list1.add(leaf5);
        incorrectOperator = InternalNode.build(list1);

        List<Node> list2 = new ArrayList<>();
        list2.add(leaf1);
        list2.add(leaf4);
        list2.add(leaf5);
        badFunctionInput = InternalNode.build(list2);

        List<Node> list3 = new ArrayList<>();
        list3.add(leaf1);
        list3.add(leaf4);
        list3.add(leaf3);
        applyFunctionToFunction = InternalNode.build(list3);

        List<Node> list4 = new ArrayList<>();
        list4.add(leaf3);
        list4.add(leaf4);
        list4.add(leaf5);
        applyFunctionToVariable = InternalNode.build(list4);

        List<Node> list5 = new ArrayList<>();
        list5.add(leaf1);
        list5.add(leaf2);
        list5.add(leaf3);
        list5.add(leaf4);
        list5.add(leaf5);
        andThenFunctionToFunction = InternalNode.build(list5);

        List<Node> list6 = new ArrayList<>();
        list6.add(leaf3);
        list6.add(leaf2);
        list6.add(leaf3);
        list6.add(leaf4);
        list6.add(leaf5);
        andThenFunctionIncompatibleTypes = InternalNode.build(list6);

        List<Node> list7 = new ArrayList<>();
        list7.add(leaf1);
        list7.add(leaf2);
        list7.add(leaf5);
        andThenFunctionToVariable = InternalNode.build(list7);

        List<Node> list8 = new ArrayList<>();
        list8.add(leaf5);
        list8.add(leaf2);
        list8.add(leaf5);
        sameType = InternalNode.build(list8);

        List<Node> list9 = new ArrayList<>();
        list9.add(leaf1);
        singleLeaf = InternalNode.build(list9);
    }

    @Test
    public void main() {
        Driver.main(null);
    }

    @Test
    public void main_type_mismatch(){
        Driver.setInternalNode(incompatibleTypes);
        Driver.main(null);
    }

    @Test
    public void main_incorrect_operator(){
        Driver.setInternalNode(incorrectOperator);
        Driver.main(null);
    }

    @Test
    public void main_incorrect_function_input(){
        Driver.setInternalNode(badFunctionInput);
        Driver.main(null);
    }

    @Test
    public void main_apply_to_function(){
        Driver.setInternalNode(applyFunctionToFunction);
        Driver.main(null);
    }

    @Test
    public void main_apply_to_function_nominal(){
        Driver.setInternalNode(applyFunctionToVariable);
        Driver.main(null);
    }

    @Test
    public void main_and_then_function_nominal(){
        Driver.setInternalNode(andThenFunctionToFunction);
        Driver.main(null);
    }

    @Test
    public void main_and_then_function_bad_input(){
        Driver.setInternalNode(andThenFunctionIncompatibleTypes);
        Driver.main(null);
    }

    @Test
    public void main_and_then_function_to_value(){
        Driver.setInternalNode(andThenFunctionToVariable);
        Driver.main(null);
    }

    @Test
    public void main_empty_node(){
        Driver.setInternalNode(andThenFunctionToVariable);
        Driver.main(null);
    }

    @Test
    public void main_repeated_type(){
        Driver.setInternalNode(sameType);
        Driver.main(null);
    }

    @Test
    public void main_single_leaf(){
        Driver.setInternalNode(singleLeaf);
        Driver.main(null);
    }


}