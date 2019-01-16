package zxh.activemq.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 接收消息监听
 * 非阻塞接收
 */
public class SpringJmsListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            String msg = ((TextMessage)message).getText();
            System.out.println("非阻塞接收消息："+msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
