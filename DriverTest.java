import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DriverTest {

    private Variable f = Variable.build("f");

    private LeafNode leaf1 = LeafNode.build(f);

    private List<Node> badList = new ArrayList<>();

    InternalNode badNode;

    @Before
    public void setUp(){
        badList.add(leaf1);
        badNode = InternalNode.build(badList);
    }

    @Test
    public void main() {
        Driver.main(null);
    }

    @Test
    public void main_bad_node(){
        Driver.setNode(badNode);
        Driver.main(null);
    }

}