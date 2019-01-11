import org.junit.Test;
import zxh.mongodb.spring_data_mongodb.entity.UserPO;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by xh.zhi on 2019-1-10.
 */
public class OtherTest {

    public void parseEntity(Class entityClass){
        Class parseClass = entityClass;


    }

    @Test
    public void annotionTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {


        Class userClass =UserPO.class;

        Field[] fields = userClass.getDeclaredFields();
        for (Field field : fields){
            if(field.isAnnotationPresent(org.springframework.data.mongodb.core.mapping.Field.class)){
                org.springframework.data.mongodb.core.mapping.Field mgField = field.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class);
                String mgfieldName = mgField.value();
                String fieldName = field.getName();

                String fieldName_ =  fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length());
                String getMethodName = "get"+fieldName_;

                if(mgfieldName==null || mgfieldName.length()==0){
                    mgfieldName = fieldName;
                }

                Method method = userClass.getMethod(getMethodName,userClass);
                Object value = method.invoke(userClass);
                System.out.println("mgfieldName=" +mgfieldName+"; "+getMethodName+"="+value);
            }
        }

    }

    @Test
    public void exceptionTest(){
        System.out.println("===start=====");
        int i=0;
        if(i==0){
            throw  new RuntimeException("测试exception");
        }
        System.out.println("======end=====");
    }

    @Test
    public void test(){
        String s = "age  ";
        String  orderItem_ = s.toUpperCase();
        String replaceStr = orderItem_.indexOf("ASC")>=0?"ASC":orderItem_.indexOf("DESC")>=0?"DESC":"ASC";
        String orderAttr = orderItem_.replace(replaceStr,"").trim();
        String orderReg = replaceStr;
        System.out.println(orderAttr +" "+orderReg);
    }
}
