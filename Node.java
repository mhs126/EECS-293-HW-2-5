package Parser;
import java.util.List;
interface Node{

    List<Token> toList();

    List<Node> getChildren();

    boolean isFruitful();

}