package zxh.activemq.consumer;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;


public class SpringReciveMsg {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:activemq/activemq.xml");
        JmsTemplate jmsAmqTemplate = (JmsTemplate) ctx.getBean("jmsAmqTemplate");
        String msg = (String) jmsAmqTemplate.receiveAndConvert();


        System.out.println("收到信息："+msg);


    }
}
