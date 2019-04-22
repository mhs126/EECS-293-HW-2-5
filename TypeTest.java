import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TypeTest {

    public Type integer;
    public Type dub;
    public Set<Type> integerSet;

    @Before
    public void createTypes(){
        integer = Type.build("int");
        dub = Type.build("double");
        integerSet = new HashSet<Type>(Arrays.asList(dub));
    }

    @org.junit.Test
    public void toStringTest() {
        assertEquals("int", integer.toString());
    }

    @org.junit.Test
    public void addToHigher() {
        integer.addToHigher(dub);
        assertEquals(integerSet, integer.higherSet);
        assertEquals(integerSet, integer.compatible);
    }

    @org.junit.Test
    public void addToLower() {
        integer.addToLower(dub);
        assertEquals(integerSet, integer.lowerSet);
        assertEquals(integerSet, integer.compatible);
    }

    @org.junit.Test
    public void isFunction() {
        assertFalse(integer.isFunction());
    }

}