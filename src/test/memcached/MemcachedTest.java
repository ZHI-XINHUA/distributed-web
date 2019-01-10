import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zxh.memcached.service.SpyService;
import zxh.memcached.entity.User;

/**
 * 单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class MemcachedTest {

    @Autowired
    SpyService spyService;

    @Test
    public void addCacheTest(){
        User user = new User();
        user.setName("zxh");
        user.setAge(20);
        user.setAddress("广东广州");

        spyService.putCache("user",6000,user);
    }

    @Test
    public void getCacheTest(){
        User user = (User)spyService.getCache("user");
        System.out.println(user.toString());
    }
}
