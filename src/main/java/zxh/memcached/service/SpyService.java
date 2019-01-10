package zxh.memcached.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zxh.memcached.config.SpyMemcachedManager;

/**
 * memcached缓存service
 *   下面只提供几个简单的操作
 */
@Service
public class SpyService {
    @Autowired
    SpyMemcachedManager  spyMemcachedManager;

    /**
     * 设置缓存
     * @param key
     * @param exp
     * @param cacheObj
     */
    public void  putCache(String key, int exp, Object cacheObj){
        spyMemcachedManager.add(key,cacheObj,exp);
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public Object getCache(String key){
        return spyMemcachedManager.get(key);
    }

    /**
     * 删除
     * @param key
     */
    public void delCache(String key){
        spyMemcachedManager.delete(key);
    }
}
