import java.util.*;
import java.util.function.Function;
final class Cache<T, V> {
  
  private Map<T, V> cache;
  
  public V get(T key, Function<? super T, ? extends V> constructor){
    if(cache.get(key) != null)
      return cache.get(key);
    else{
      return constructor.apply(key);
    }
  }
  
}