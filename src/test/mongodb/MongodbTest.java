import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import zxh.mongodb.spring_data_mongodb.dao.IUserDao;
import zxh.mongodb.spring_data_mongodb.entity.UserPO;
import zxh.mongodb.spring_data_mongodb.service.MongodbService;
import zxh.mongodb.spring_data_mongodb.util.PageModel;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
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

    /**
     * 保存
     */
    @Test
    public void saveDaoTest(){
        UserPO user = createUser();
        mgUserDao.addUser(user);
    }

    @Test
    public void saveServiceTest(){
        UserPO user = createUser();
        mgdbService.saveUser(user);
    }

    @Test
    public void saveMuiltTest(){
        for(int i=1;i<=100;i++){
            UserPO user = new UserPO();
            user.setId(UUID.randomUUID().toString());
            user.setName("olay_"+i);
            user.setAge(new Random().nextInt(101));
            user.setBirth(new Timestamp(System.currentTimeMillis()));
            user.setSex(1);
            user.setAddress("广东广州");
            mgdbService.saveUser(user);
        }
    }

    /**
     * 更新
     */
    @Test
    public void updateTest(){
        UserPO user = new UserPO();
        user.setId("4225ce84-b1bd-4a98-b02b-bf54e62753d9");
        user.setName("kobe");
        user.setAge(30);
        //user.setBirth(new Timestamp(System.currentTimeMillis()));
        user.setSex(1);
        user.setAddress("洛杉矶4点");
        mgUserDao.update(user);
    }

    @Test
    public void findAllTest(){
        List<UserPO> list = mgUserDao.findAll();
        for(UserPO user:list){
            System.out.println(user.toString());
        }
    }

    @Test
    public void findTest(){
        UserPO user = mgUserDao.findOne("4225ce84-b1bd-4a98-b02b-bf54e62753d9");
        System.out.println(user);
    }

    @Test
    public void deleteTest(){
        mgUserDao.delete("84ba981c-3483-4ad4-956c-f31c4407c909");
    }

    @Test
    public void finaAllOrderTest(){
        List<UserPO> list = mgUserDao.findAll("age asc ");
        for(UserPO user:list){
            System.out.println(user.toString());
        }
    }

    @Test
    public void  findByParam(){
        List<UserPO> list = mgUserDao.findByParam("namecn","kobe");
        for(UserPO user:list){
            System.out.println(user.toString());
        }
        System.out.println("=====findByParam order ========");
        list = mgUserDao.findByParam("sex",1," age desc");
        for(UserPO user:list){
            System.out.println(user.toString());
        }

        System.out.println("=====findByParams  ========");
        list = mgUserDao.findByParams(new String[]{"sex","address"},new Object[]{1,"广东广州"});
        for(UserPO user:list){
            System.out.println(user.toString());
        }


        System.out.println("=====findByParams order ========");
        list = mgUserDao.findByParams(new String[]{"sex","address"},new Object[]{1,"广东广州"}," age desc");
        for(UserPO user:list){
            System.out.println(user.toString());
        }

    }

    @Test
    public void uniqueByParamsTest(){
        UserPO userPO = mgUserDao.uniqueByParam("namecn","kobe");
        System.out.println(userPO.toString());

        System.out.println("============");
        userPO = mgUserDao.uniqueByParams(new String[]{"sex","address"},new Object[]{1,"广东广州"});
        System.out.println(userPO.toString());
    }

    @Test
    public void countByCondition(){
        int count  = mgUserDao.count(null,null);
        System.out.println("count="+count);

        System.out.println("============");

        count  = mgUserDao.count(new String[]{"sex","address"},new Object[]{1,"广东广州"});
        System.out.println("count="+count);
    }

    @Test
    public void pageAll(){
        PageModel<UserPO> page = mgUserDao.pageAll(1,10);
        printPageInfo(page);

       page = mgUserDao.pageAll(2,10);
        printPageInfo(page);
    }

    @Test
    public void pageAllOrder(){
        PageModel<UserPO> page = mgUserDao.pageAll(1,10,"age desc");
        printPageInfo(page);

        page = mgUserDao.pageAll(2,10,"age desc");
        printPageInfo(page);
    }



    private void printPageInfo(PageModel<UserPO> page){
        System.out.println("当前页码:"+page.getPageNo()+" 总页数："+page.getPageSize()+" 总数："+page.getTotalCount());
        System.out.println("结果如下：");
        List<UserPO> list = page.getResult();
        for(UserPO user:list){
            System.out.println(user.toString());
        }
        System.out.println();
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
