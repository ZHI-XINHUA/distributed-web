package zxh.memcached.config;

import net.spy.memcached.MemcachedClient;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * 暂时没用上，后续补充（主要是将memcached整合到spring提供的缓存接口中）
 */
public class SpyMemCache {
    private static  Logger logger = Logger.getLogger(SpyMemCache.class);

    /**保存缓存的key**/
    private Set<String> keySet = new HashSet<String>();
    /**名称**/
    private String name;
    /**失效时间**/
    private int expire;
    /**memcached 客户端**/
    private MemcachedClient memClient;

    public SpyMemCache(String name,int expire,MemcachedClient memClient){
        this.name = name;
        this.expire = expire;
        this.memClient = memClient;
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public Object get(String key){
        if(StringUtils.isEmpty(key)){
            logger.error("key为空，不能获取缓存值！");
            return null;
        }
        Object cacheValue = null;
        String key_ = getKey(key);
        try{
            cacheValue = memClient.get(key_);
        }catch (Exception e){
            logger.error("key："+key+",获取缓存失败！");
            e.printStackTrace();
        }
       return cacheValue;
    }

    /**
     * 设置缓存
     * @param key
     * @param value
     */
    public void put(String key,Object value){
        if(StringUtils.isEmpty(key)){
            logger.error("key为空，不能设置缓存！");
            return;
        }
        String key_ = getKey(key);
        try{
            memClient.set(key_,expire,value);
            //缓存key添加到容器中
            keySet.add(key_);
        }catch (Exception e){
            logger.error("设置缓存失败！");
            e.printStackTrace();
        }
    }

    /**
     * 删除缓存
     * @param key
     */
    public void delete(String key){
        String key_ = getKey(key);
        try{
            memClient.delete(key_);
            keySet.remove(key_);
        }catch (Exception e){
            logger.error("key："+key+"，删除缓存失败！");
        }
    }

    /**
     * 清除所有缓存
     */
    public void clear(){
        Set<String> set = keySet;
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            memClient.delete(key);
        }
        keySet.clear();
    }





    private String getKey(String key){
        return ((StringUtils.isEmpty(name))?"":name+"_")+key;
    }

    private String getKeyHash(String key){
        return  getKey(key).hashCode()+"";
    }



}
