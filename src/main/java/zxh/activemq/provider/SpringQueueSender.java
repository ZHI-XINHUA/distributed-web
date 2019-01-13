package zxh.activemq.provider;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
//https://www.cnblogs.com/winner-0715/p/6246480.html
public class SpringQueueSender {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:activemq/activemq.xml");
        JmsTemplate jmsAmqTemplate = (JmsTemplate) ctx.getBean("jmsAmqTemplate");
        jmsAmqTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage();
                message.setText(" spring activemq");
                return message;
            }
        });
    }


}
