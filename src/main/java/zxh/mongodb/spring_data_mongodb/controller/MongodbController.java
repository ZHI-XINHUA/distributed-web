package zxh.mongodb.spring_data_mongodb.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zxh.mongodb.spring_data_mongodb.entity.UserPO;
import zxh.mongodb.spring_data_mongodb.service.MongodbService;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by xh.zhi on 2019-1-9.
 */
@Controller
@RequestMapping("/mgdb")
public class MongodbController {
    private static Logger logger = Logger.getLogger(MongodbController.class);

    @Autowired
    MongodbService mgdbService;

    @RequestMapping("/addUser")
    @ResponseBody
    public String addUser(){
        logger.info("请求进入 /mgdb/addUser");
        UserPO user = new UserPO();
        user.setId(UUID.randomUUID().toString());
        user.setAge(10);
        user.setBirth(new Timestamp(System.currentTimeMillis()));
        user.setSex(0);
        user.setAddress("广东广州");
        mgdbService.saveUser(user);
        return "添加成功";
    }
}
