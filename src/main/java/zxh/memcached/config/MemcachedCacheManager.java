package zxh.memcached.config;

import net.spy.memcached.MemcachedClient;
import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 暂时没用上，后续补充（主要是将memcached整合到spring提供的缓存接口中）
 */
public class MemcachedCacheManager extends AbstractTransactionSupportingCacheManager {

    private ConcurrentHashMap<String ,Cache> cacheMap = new ConcurrentHashMap<String,Cache>();

    private Map<String,Integer> expireMap = new HashMap<String,Integer>();

    private MemcachedClient memcachedClient;


    @Override
    protected Collection<? extends Cache> loadCaches() {
        Collection<Cache> collection = cacheMap.values();
        return collection;
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = cacheMap.get(name);
        if(cache==null){
            Integer expire = expireMap.get(name);
            if(expire==null){
                expire = 0;
                expireMap.put(name,expire);
            }
            cache = new MemcachedCache(name,expire,memcachedClient);
            cache.put(name,cache);
        }
        return cache;
    }

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public void setConfigMap(Map<String,Integer> expireMap){
        this.expireMap = expireMap;
    }
}
