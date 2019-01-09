package zxh.mongodb.spring_data_mongodb.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import zxh.mongodb.spring_data_mongodb.dao.IUserDao;
import zxh.mongodb.spring_data_mongodb.entity.UserPO;



/**
 * 用户模块dao
 */
@Repository("mgUserDao")
public class UserDaoImpl  extends  BaseDaoImpl<UserPO> implements IUserDao {
    private static  Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    protected Class<UserPO> getEntityClass() {
        return UserPO.class;
    }

    @Override
    public void addUser(UserPO user) {
        logger.info("添加用户>>>UserDaoImpl!addUser()");
        save(user);
    }
}
