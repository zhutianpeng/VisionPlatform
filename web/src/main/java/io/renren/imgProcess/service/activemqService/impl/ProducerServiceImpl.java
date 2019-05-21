package io.renren.imgProcess.service.activemqService.impl;

import io.renren.imgProcess.service.activemqService.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.ProducerCallback;
import org.springframework.jms.core.SessionCallback;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.io.Serializable;

@Service
public class ProducerServiceImpl implements ProducerService {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendMessage(Destination destination, final String message) {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
				/*TextMessage textMessage = session.createTextMessage(message);
				textMessage.setJMSReplyTo(responseDestination);
				return textMessage;*/
                return session.createTextMessage(message);
            }
        });
    }


    @Override
    public void sendMessage(Destination destination, Serializable obj) {
        //使用MessageConverter的情况
        jmsTemplate.convertAndSend(destination, obj);
        jmsTemplate.execute(new SessionCallback<Object>() {

            @Override
            public Object doInJms(Session session) throws JMSException {
                MessageProducer producer = session.createProducer(destination);
                Message message = session.createObjectMessage(obj);
                producer.send(message);
                return null;
            }

        });
        jmsTemplate.execute(new ProducerCallback<Object>() {

            @Override
            public Object doInJms(Session session, MessageProducer producer)
                    throws JMSException {
                Message message = session.createObjectMessage(obj);
                producer.send(destination, message);
                return null;
            }

        });
    }


}
