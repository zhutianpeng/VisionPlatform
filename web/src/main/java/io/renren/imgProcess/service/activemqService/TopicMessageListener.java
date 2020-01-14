package io.renren.imgProcess.service.activemqService;

import net.sf.json.JSONObject;
import org.springframework.jms.annotation.JmsListener;
import redis.clients.jedis.Jedis;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
发布订阅模式的监听器
*/
public class TopicMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("TopicConsumer接收来自生产者的消息：");
        System.out.println(message.getClass().getName());
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage)message;
            try {
                System.out.println("TopicConsumer接收到的消息是：" + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
