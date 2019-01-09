package zxh.mongodb.spring_data_mongodb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zxh.mongodb.spring_data_mongodb.dao.IUserDao;
import zxh.mongodb.spring_data_mongodb.entity.UserPO;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by xh.zhi on 2019-1-8.
 */
@Service("mgdbService")
public class MongodbService {
    @Autowired
    IUserDao mgUserDao;

    public void saveUser(UserPO user){
        mgUserDao.addUser(user);
    }
}
