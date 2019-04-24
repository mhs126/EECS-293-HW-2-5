/**
 * Exception thrown if two Types in an InternalNode are incompatible.
 * Two Types are incompatible if they do not appear in each others compatible
 * lists.
 */
public class IncompatibleTypeException extends Exception {

    public IncompatibleTypeException(String message){
        super(message);
    }

}
