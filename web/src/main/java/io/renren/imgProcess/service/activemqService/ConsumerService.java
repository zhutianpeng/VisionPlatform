package io.renren.imgProcess.service.activemqService;
import javax.jms.JMSException;

public interface ConsumerService {
    public void receiveMessage(String message) throws JMSException;
}
