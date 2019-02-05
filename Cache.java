package Parser;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
final class Cache<T, V> {

    private Map<T, V> cache = new HashMap<T,V>();

    public V get(T key, Function<? super T, ? extends V> constructor){
        if(key == null){
            throw new NullPointerException("Key is Null");
        }
        else if(constructor == null){
            throw new NullPointerException("No Function found");
        }
        else if(cache.get(key) != null){
            return cache.get(key);
        }
        else{
            V obj = constructor.apply(key);
            cache.put(key, obj);
            return obj;
        }
    }
}
