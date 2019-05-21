package io.renren.imgProcess.service.activemqService.impl;

import io.renren.imgProcess.service.activemqService.ConsumerService;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Override
    public void receiveMessage(String message) throws JMSException {
        System.out.println("-----------------生产者发来的消息是：" + message);
    }
}
