import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zxh.mongodb.spring_data_mongodb.entity.UserPO;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * MongoTemplate api 单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MongoTemplateTest {

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 保存
     */
    @Test
    public void save(){
        UserPO user = new UserPO();
        user.setId(UUID.randomUUID().toString());
        user.setName("james");
        user.setAge(34);
        user.setBirth(new Timestamp(System.currentTimeMillis()));
        user.setSex(1);
        user.setAddress("克利夫兰");
        //插入
        mongoTemplate.save(user);
    }

    /**
     * 更新
     */
    @Test
    public void update(){
        Query query = new Query();
        //where过滤的条件
        query.addCriteria(Criteria.where("name").is("james"));
        query.addCriteria(Criteria.where("age").is(34));

        //修改值
        Update update = new Update();
        update.set("address","洛杉矶");

        mongoTemplate.updateFirst(query,update,UserPO.class);
    }

    /**
     * findById
     */
    @Test
    public void findByIdTest(){
        UserPO userPO = mongoTemplate.findById("4225ce84-b1bd-4a98-b02b-bf54e62753d9",UserPO.class);
        System.out.println(userPO.toString());
    }

    @Test
    public void findOrder(){
        Sort.Order order = new Sort.Order(Sort.Direction.DESC,"age");
        Query query  = new Query();
        query.with(new Sort(order)); //添加排序
        List<UserPO> list = mongoTemplate.find(query,UserPO.class);
        for (UserPO user:list){
            System.out.println(user.toString());
        }
    }



    /**
     * 解析实体类 @Id和@Field的字段和值
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    public void annotionTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        UserPO user = new UserPO();
        user.setId(UUID.randomUUID().toString());
        user.setName("james");
        user.setAge(34);
        user.setBirth(new Timestamp(System.currentTimeMillis()));
        user.setSex(1);
        user.setAddress("克利夫兰");

        Class userClass =user.getClass();

        String mgfieldName = "";
        String fieldName = "";

        Field[] fields = userClass.getDeclaredFields();
        for (Field field : fields){
            //解析@Id
            if(field.isAnnotationPresent(Id.class)){
                fieldName = field.getName();

            }

            //解析@Field注解的字段，获取名称和值
            if(field.isAnnotationPresent(org.springframework.data.mongodb.core.mapping.Field.class)){
                org.springframework.data.mongodb.core.mapping.Field mgField = field.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class);
                mgfieldName = mgField.value();
                fieldName = field.getName();
            }

            if(fieldName==null || fieldName.length()==0){
                continue;
            }


            String fieldName_ =  fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length());
            String getMethodName = "get"+fieldName_;

            if(mgfieldName==null || mgfieldName.length()==0){
                mgfieldName = fieldName;
            }

            Method method = userClass.getDeclaredMethod(getMethodName);
            if(method.getModifiers()!= Modifier.PUBLIC){
                continue;
            }
            Object value = method.invoke(user);

            System.out.println("mgfieldName=" +mgfieldName+";fieldName="+fieldName+ ";"+getMethodName+"="+value);
        }

    }
}
