package ji.util;

/**
 * Created by ji on 16-2-4.
 */
public interface ExpireCallBack<K, V> {

    public void handler(K key, boolean isEnd) throws Exception;

}
