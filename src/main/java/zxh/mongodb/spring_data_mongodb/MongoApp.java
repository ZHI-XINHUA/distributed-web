package zxh.mongodb.spring_data_mongodb;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import zxh.mongodb.spring_data_mongodb.byxml.MongoDBService;
import zxh.mongodb.spring_data_mongodb.byxml.Person;

import javax.annotation.Resource;
import java.util.Set;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Created by xh.zhi on 2019-1-4.
 */
public class MongoApp {
    private static final Log log = LogFactory.getLog(MongoApp.class);

    @Resource(name = "mongoDBService")
    MongoDBService dbService;

    public static void main(String[] args) throws Exception {

       /* MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "database");
        mongoOps.insert(new Person("Joe", 34));

        log.info(mongoOps.findOne(new Query(where("name").is("Joe")), Person.class));

        mongoOps.dropCollection("person");*/


        /*Mongo mongo = new Mongo("192.168.3.31",27017);
        //根据mongodb数据库的名称获取mongodb对象 ,
        DB db = mongo.getDB( "testdb" );
        Set<String> collectionNames = db.getCollectionNames();
        // 打印出test中的集合
        for (String name : collectionNames) {
            System.out.println("collectionName==="+name);
        }*/


    }
}
