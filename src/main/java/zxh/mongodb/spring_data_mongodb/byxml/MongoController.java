package zxh.mongodb.spring_data_mongodb.byxml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by xh.zhi on 2019-1-4.
 */
@Controller
@RequestMapping("/mongo")
public class MongoController {

    @Resource(name = "mongoDBService")
    MongoDBService service;

    @RequestMapping("/save")
    public void save(){
        System.out.println("===save===");
        service.save(new Person("zxh",18));
    }
}
