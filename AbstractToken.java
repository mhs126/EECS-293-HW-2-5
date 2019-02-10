package Parser;
public abstract class AbstractToken implements Token{

    //Returns true if the instance matches the input type
    public final boolean matches(TerminalSymbol type){
        return (type == this.getType());
    }

}