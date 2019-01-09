package zxh;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试
 */
@Controller
@RequestMapping("/test")

public class TestController {

    @RequestMapping("/hello")
    public String hello(){
        System.out.println("zzz");
        return "hello";
    }
}
