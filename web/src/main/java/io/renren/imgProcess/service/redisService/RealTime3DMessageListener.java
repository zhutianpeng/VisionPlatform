package io.renren.imgProcess.service.redisService;

import io.renren.imgProcess.service.activemqService.ProducerService;
import net.sf.json.JSONObject;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;


@Service
public class RealTime3DMessageListener implements MessageListener {

    @Autowired
    private ProducerService producerService;

    @Override
    public void onMessage(Message message, byte[] pattern) { //实时3D数据接收测试用
        JSONObject jsonObject = JSONObject.fromObject(message.toString()); //TODO
        String poseData = jsonObject.getString("poseData");
        String userToken = jsonObject.getString("userToken");
        String targetUrl = "/user/" + userToken + "/realTime3DOutput";
        ActiveMQQueue destination = new ActiveMQQueue(targetUrl);
        producerService.sendMessage(destination, poseData);
    }
}
