package zxh.memcached.config;

import net.spy.memcached.MemcachedClient;
import org.apache.log4j.Logger;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.concurrent.Callable;

/**
 * 暂时没用上，后续补充（主要是将memcached整合到spring提供的缓存接口中）
 */
public class MemcachedCache implements Cache{
    private static Logger LOG = Logger.getLogger(MemcachedCache.class);

    private String name;
    private SpyMemCache memCache;

    public MemcachedCache(String name, int expire, MemcachedClient memcachedClient){
        this.name = name;
        memCache = new SpyMemCache(name,expire,memcachedClient);
    }

    /**
     * 获取 cache 名称
     * @return
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * 获取真正的缓存提供方案
     * @return
     */
    @Override
    public SpyMemCache getNativeCache() {
        return memCache;
    }


    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper valueWrapper = null;
        Object cacheValue = memCache.get(key.toString());
        if(cacheValue!=null){
            valueWrapper = new SimpleValueWrapper(cacheValue);
        }
        return valueWrapper;
    }

    /**
     * 从缓存中获取 key 对应的指定类型的值
     * @param key
     * @param aClass
     * @param <T>
     * @return
     */
    @Override
    public <T> T get(Object key, Class<T> aClass) {
        Object cacheValue = memCache.get(key.toString());
        if(cacheValue==null){
            return null;
        }
        if(aClass!=null && !aClass.isInstance(cacheValue)){
            LOG.error("缓存类型转换失败");
            return null;
        }
        return (T)cacheValue;
    }


    /**
     *
     * 从缓存中获取 key 对应的值，如果缓存没有命中，则添加缓存，
     * 此时可异步地从 valueLoader 中获取对应的值（4.3版本新增）
     *
     * @param key
     * @param callable
     * @param <T>
     * @return
     */
    @Override
    public <T> T get(Object key, Callable<T> callable) {
        return null;
    }

    /**
     * 添加缓存
     * @param key
     * @param value
     */
    @Override
    public void put(Object key, Object value) {
        memCache.put(key.toString(),value);
    }


    /**
     * 缓存 key-value，如果缓存中已经有对应的 key，则返回已有的 value，不做替换，并返回null
     * @param key
     * @param value
     * @return
     */
    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        Object cacheObj = memCache.get(key.toString());
        if(cacheObj==null){
            memCache.put(key.toString(),value);
            return null;
        }
        ValueWrapper wrapper = new SimpleValueWrapper(cacheObj);
        return wrapper;
    }

    /**
     *  从缓存中移除对应的 key
     * @param key
     */
    @Override
    public void evict(Object key) {
        memCache.delete(key.toString());
    }

    /**
     * 清空缓存
     */
    @Override
    public void clear() {
        memCache.clear();
    }
}
