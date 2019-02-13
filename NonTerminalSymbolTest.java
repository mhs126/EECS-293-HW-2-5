package Parser;

public class NonTerminalSymbolTest{

    Variable a = Variable.build("a");
    Variable b = Variable.build("b");
    Variable c = Variable.build("c");
    Connector plus = Connector.build(TerminalSymbol.PLUS);
    Connector divide = Connector.build(TerminalSymbol.DIVIDE);
    List<Token> list = new ArrayList<>();
    list.add(a);
    list.add(plus);
    list.add(b);
    list.add(divide);
    list.add(c);

    public void testParseInput(){
        System.out.println( EXPRESSION.parseInput(list).toString());
    }
}