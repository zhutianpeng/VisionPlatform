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
public class RedisMessageListener implements MessageListener {
    @Autowired
    private ChannelTopic channelTopic;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private ProducerService producerService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        JSONObject json = JSONObject.fromObject(message.toString());
        String imageID = json.getString("imageID");

        Jedis jedis = jedisPool.getResource();
        String imageContent = jedis.hget(imageID, "image"); //  原图
        String userToken = jedis.hget(imageID, "userToken");//  user

//        output
        String imageResult = imageContent;

//        pose
        String poseResultString = jedis.hget(imageID, "poseResult");
        if(StringUtils.isNotBlank(poseResultString)){
            imageResult = PoseUtils.drawHumans(poseResultString, imageContent);
        }

//        face
        String faceResultString = jedis.hget(imageID, "faceResult");
        if(StringUtils.isNotBlank(faceResultString)){
            imageResult = FaceUtils.drawFaces(faceResultString, imageResult);
        }

        String poseResultParsed=null;
//        get pose result ArrayList
        if(StringUtils.isNotBlank(poseResultString) && !poseResultString.equals("[]")){
            poseResultParsed = PoseUtils.getPoseData(poseResultString, imageContent);
        }

        Map<String,String> result = new HashMap<String, String>();
        result.put("image", imageResult);

        if(StringUtils.isNotBlank(poseResultParsed)){
            result.put("poseResultParsed", poseResultParsed);
        }

        JSONObject output = JSONObject.fromObject(result);


//       redis 释放资源
        jedis.del(imageID);
        jedis.close();

//       activeMQ send for video and poseResultParsed
        ActiveMQQueue destination = new ActiveMQQueue("/user/"+ userToken +"/video");

        producerService.sendMessage(destination, output.toString());
//
    }
}
