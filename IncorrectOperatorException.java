/**
 * Exception thrown when a - or / operator appears after a Function.
 * Only + and * may come after a Function.
 */
public class IncorrectOperatorException extends Exception {

    public IncorrectOperatorException(String message){
        super(message);
    }

}
