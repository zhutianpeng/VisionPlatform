package io.renren.imgProcess.service.redisService;

import com.alibaba.fastjson.JSONObject;
import io.renren.imgProcess.service.activemqService.ProducerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class VelocityMessageListener implements MessageListener {

    @Autowired
    private ProducerService producerService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        JSONObject result = JSONObject.parseObject(message.toString());
        String userToken = result.getString("userToken");
        ActiveMQQueue destination = new ActiveMQQueue("/user/"+ userToken +"/velocityOutput");
        producerService.sendMessage(destination, result.toString());  //发到ActiveMQ中

    }
}