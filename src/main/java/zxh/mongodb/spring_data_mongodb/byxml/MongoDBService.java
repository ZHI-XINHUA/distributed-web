package zxh.mongodb.spring_data_mongodb.byxml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * Created by xh.zhi on 2019-1-4.
 */
@Service("mongoDBService")
public class MongoDBService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public <T> T findOne(Query query, Class<T> entityClass){

        return  mongoTemplate.findOne(query,entityClass);
    }

    public void save(Object obj){
        mongoTemplate.save(obj);
    }
}
