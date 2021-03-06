package zxh.activemq.consumer;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;

/**
 * 消息接收端
 */
public class SpringReciveMsg {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:activemq/activemq.xml");
        //获取JmsTemplate
        JmsTemplate jmsAmqTemplate = (JmsTemplate) ctx.getBean("jmsAmqTemplate");
        //获取消息
        String msg = (String) jmsAmqTemplate.receiveAndConvert();

        System.out.println("收到信息："+msg);

    }
}
