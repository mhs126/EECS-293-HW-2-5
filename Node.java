import java.util.List;
import java.util.Optional;

interface Node{

    List<Token> toList();

    List<Node> getChildren();

    boolean isFruitful();

    boolean isOperator();

    boolean isStartedByOperator();

    boolean isSingleLeafParent();

}