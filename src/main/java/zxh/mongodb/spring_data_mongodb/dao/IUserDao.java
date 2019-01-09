package zxh.mongodb.spring_data_mongodb.dao;

import zxh.mongodb.spring_data_mongodb.entity.UserPO;

/**
 * User dao
 */
public interface IUserDao extends IBaseDao<UserPO> {
    /**
     * 添加用户
     * @param user
     */
    void addUser(UserPO user);
}
