import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
final class Cache<T, V> {

    private Map<T, V> cache = new HashMap<T,V>();

    /*
        Takes a key and a constructor
        Checks if the key is null, returns the object associated with the key if it is not
        Checks if the constructor is null, puts the key and object pair in the cache if it is not
        returns the object
     */
    public V get(T key, Function<? super T, ? extends V> constructor){
        if(cache.get(Objects.requireNonNull(key, "Key is null, please enter valid key")) != null){
            return cache.get(key);
        }
        else{
            V obj = Objects.requireNonNull(constructor,
                    "Constructor is null, please enter valid constructor").apply(key);
            cache.put(key, obj);
            return obj;
        }
    }
}
