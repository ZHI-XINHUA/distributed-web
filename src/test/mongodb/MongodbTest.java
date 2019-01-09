import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import zxh.mongodb.spring_data_mongodb.dao.IUserDao;
import zxh.mongodb.spring_data_mongodb.entity.UserPO;
import zxh.mongodb.spring_data_mongodb.service.MongodbService;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * mongodb 测试类
 */
@RunWith(SpringJUnit4ClassRunner.class) //测试运行类
//@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml","classpath:applicationContext.xml"})
@ContextConfiguration(locations="classpath:applicationContext.xml") //加载配置文件
//@Transactional   //开启事务
public class MongodbTest {

    @Autowired
    MongodbService mgdbService;

    @Autowired
    IUserDao mgUserDao;

    @Test
    public void testSaveDao(){
        UserPO user = createUser();
        mgUserDao.addUser(user);
    }

    @Test
    public void testSaveService(){
        UserPO user = createUser();
        mgdbService.saveUser(user);
    }

    private UserPO createUser(){
        UserPO user = new UserPO();
        user.setId(UUID.randomUUID().toString());
        user.setName("olay1");
        user.setAge(60);
        user.setBirth(new Timestamp(System.currentTimeMillis()));
        user.setSex(1);
        user.setAddress("广东广州");
        return user;
    }

}
