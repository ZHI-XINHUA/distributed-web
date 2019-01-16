package zxh.activemq.consumer;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import java.io.IOException;

/**
 * 非阻塞接收消息，这里加载文件就好
 */
public class SpringNoBlockReciveMsg {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:activemq/activemq.xml");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
