import java.util.HashSet;
import java.util.Set;

/**
 * Type class for typecheck project
 * Creates a Type ie: double, int, string
 * Types posses a name, sets of higher, lower and compatible types
 *
 * @author Michael Silverman
 * @author Daniel Shalmiyev
 */
public class Type {

    /**
     * String representation of the Type
     */
    private String name;

    /**
     * set of higher Types
     */
    private Set<Type> higherSet = new HashSet<>();

    /**
     * set of lower types
     */
    private Set<Type> lowerSet = new HashSet<>();

    /**
     * set of all compatible types
     * the combination of the higher and lower sets
     */
    private Set<Type> compatible = new HashSet<>();

    /**
     * Base case for a Type
     * All Types have failType as a lower Type
     */
    public static Type failType = new Type("Failure");



    /**
     * getter method for higher set
     * @return Set(Types) the higher set of this Type
     */
    public Set<Type> getHigherSet() {
        return higherSet;
    }

    /**
     * getter method for lower set
     * @return Set(Types) the lower set of this Type
     */
    public Set<Type> getLowerSet() {
        return lowerSet;
    }


    /**
     * getter method for compatible set
     * @return Set(Types) the compatible set of this Type
     */
    public Set<Type> getCompatible() {
        return compatible;
    }

    /**
     * cache to ensure only one Type of the
     * same name exists at one time
     */
    private static Cache<String, Type> cache = new Cache<>();

    /**
     * constructor that creates each Type
     * @param name String name of the Type
     */
    Type(String name){
        this.name = name;
        this.addToLower(failType);
    }

    /**
     * overrides the toString method of object
     * @return string name of this Type
     */
    @Override
    public String toString(){
        return name;
    }

    /**
     * build method to create a Type
     * @param name String name of the created Type
     * @return Type builds a new Type using the Cache
     */
    public static Type build(String name) {
        return cache.get(name, Type::new);
    }

    /**
     * adds a Type to the higher and compatible sets
     * of this Type
     * @param t Type being added
     */
    public void addToHigher(Type t){
        this.higherSet.add(t);
        this.compatible.add(t);
    }

    /**
     * adds a Type to the lower and compatible sets
     * of this Type
     * @param t Type being added
     */
    public void addToLower(Type t){
        this.lowerSet.add(t);
        this.compatible.add(t);
    }

    /**
     * checks if this Type is a function
     * @return boolean returns false
     */
    public boolean isFunction() {
        return false;
    }

    /**
     * overrides the equals method of Object
     * true if the Types have the same name
     * @param input Type compared to this Type
     * @return boolean
     */
    @Override
    public boolean equals(Object input) {
        Type inputType = (Type)input;
        return this.name.equals(inputType.name);
    }
}
