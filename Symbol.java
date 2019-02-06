package Parser;

import java.util.List;

interface Symbol {

    //parse method
    ParseState parse(List<Token> input);

}
