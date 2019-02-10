package Parser;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
final class Cache<T, V> {

    private Map<T, V> cache = new HashMap<T,V>();

    public V get(T key, Function<? super T, ? extends V> constructor){
        Objects.requireNonNull(key, "Key is null, please enter valid key");
        Objects.requireNonNull(constructor, "Constructor is null, please enter valid constructor");
        if(cache.get(key) != null){
            return cache.get(key);
        }
        else{
            V obj = constructor.apply(key);
            cache.put(key, obj);
            return obj;
        }
    }
}
