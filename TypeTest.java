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
    public Set<Type> highSet;

    @Before
    public void createTypes(){
        integer = Type.build("int");
        dub = Type.build("double");
        integerSet = new HashSet<Type>(Arrays.asList(dub, Type.failType));
        highSet = new HashSet<>(Arrays.asList(dub));
    }

    @org.junit.Test
    public void toStringTest() {
        assertEquals("int", integer.toString());
    }

    @org.junit.Test
    public void addToHigher() {
        integer.addToHigher(dub);
        assertEquals(highSet, integer.getHigherSet());
        assertEquals(integerSet, integer.getCompatible());
    }

    @org.junit.Test
    public void addToLower() {
        integer.addToLower(dub);
        assertEquals(integerSet, integer.getLowerSet());
        assertEquals(integerSet, integer.getCompatible());
    }

    @org.junit.Test
    public void isFunction() {
        assertFalse(integer.isFunction());
    }

}