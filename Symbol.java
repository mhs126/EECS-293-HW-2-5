package Parser;

import java.util.List;

interface Symbol {

    ParseState parse(List<Token> input);

}
