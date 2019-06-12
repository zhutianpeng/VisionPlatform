package io.renren.imgProcess.service.redisService;

import com.alibaba.fastjson.JSONObject;
import io.renren.fileCenter.controller.WebSocketController;
import io.renren.imgProcess.service.activemqService.ProducerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Pose3DMessageListener implements MessageListener {

    @Autowired
    private ProducerService producerService;

    @Override
    public void onMessage(Message message, byte[] pattern) { //实时3D数据接收测试用
        String poseData = message.toString();
        ActiveMQQueue destination = new ActiveMQQueue("/user/111/video");
        producerService.sendMessage(destination, poseData);
    }
}
