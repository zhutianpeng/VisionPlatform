package io.renren.imgProcess.service.redisService;

import io.renren.common.imgProcessTools.FaceUtils;
import io.renren.common.imgProcessTools.PoseUtils;
import io.renren.imgProcess.service.activemqService.ProducerService;
import net.sf.json.JSONObject;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AndrewKing on 10/14/2018.
 * 监听Redis，将处理完成后的姿态数据放到ActiveMQ中
 */
@Service
public class RealTime2DMessageListener implements MessageListener {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private ProducerService producerService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        Jedis jedis = jedisPool.getResource();
        JSONObject jsonObject = JSONObject.fromObject(message.toString()); //TODO

        String userToken = jsonObject.getString("userToken");
        String poseResult = jsonObject.getString("poseResult");
        String image = jsonObject.getString("image"); //处理之后的图片

//        String imageResult = null;
//        String poseResultParsed = null;
//        if(StringUtils.isNotBlank(poseResult)&& !poseResult.equals("[]")){
//            imageResult = PoseUtils.drawHumans(poseResult, rawImage); //画图（姿态）
//            poseResultParsed = PoseUtils.getPoseData(poseResult, rawImage); //解析姿态数据
//        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("image", image); //带分析结果的图片
        result.put("poseResult", poseResult); //姿态数据
        JSONObject output = JSONObject.fromObject(result);

        jedis.close();//       redis 释放资源
        ActiveMQQueue destination = new ActiveMQQueue("/user/"+ userToken +"/realTime2DOutput");
        producerService.sendMessage(destination, output.toString());  //发到ActiveMQ中
//
    }
}
