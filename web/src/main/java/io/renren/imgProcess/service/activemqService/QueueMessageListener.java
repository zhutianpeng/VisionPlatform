package io.renren.imgProcess.service.activemqService;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author AndrewKing
 * 监听ActiveMQ
 */
@Service
public class QueueMessageListener {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @JmsListener(destination = "freeInput", containerFactory = "queueListenerFactory")
    public void freeOnMessage(Message message) {
        if (message instanceof BytesMessage) {
            BytesMessage bytesMessage = (BytesMessage)message;
            try {
                // 得到一些参数：

                String userToken = String.valueOf(bytesMessage.getByteProperty("userToken"));  //用户Token
                String task = String.valueOf(bytesMessage.getByteProperty("task"));               //执行的哪一个Task
                String base64Image ="";                                            //原始图像
                UUID uuid= UUID.randomUUID();
                String imageID = uuid.toString();                                   // imageID

                byte[] buffer = new byte[1024*1024];
                int len = 0;
                while((len = bytesMessage.readBytes(buffer)) != -1){
                    base64Image = new String(buffer,0, len);
                }

                Jedis jedis = jedisPool.getResource();


                Map<String,String> imageData = new HashMap<String, String>();
                imageData.put("imageID", imageID);
                imageData.put("userToken", userToken);
                imageData.put("taskList", task);
                imageData.put("image", base64Image);

                JSONObject jsonObject = JSONObject.fromObject(imageData);

//              比较task列表并分发存入对应的redis的list

                int taskToDo = Integer.parseInt(task);
                if((taskToDo&0x01)>0) { //pose
                    jedis.rpush("image_queue_to_pose_estimation", jsonObject.toString());
                }
                if((taskToDo&0x02)>0){ //face
                    jedis.rpush("image_queue_to_face_recognition", jsonObject.toString());
                }
                if((taskToDo&0x04)>0){ //object
                    jedis.rpush("image_queue_to_object_recognition", jsonObject.toString());
                }

                //手动释放资源，不然会因为jedisPool里面的maxActive=200的限制，只能创建200个jedis资源。
                jedis.close();

            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    @JmsListener(destination = "poseRawInput", containerFactory = "queueListenerFactory")
    public void realTime3DOnMessage(Message message) {
       if (message instanceof BytesMessage) {
            BytesMessage bytesMessage = (BytesMessage)message;
            try {
                Jedis jedis = jedisPool.getResource();
                Map<String, String> imageData = new HashMap<String, String>();
                String userToken = String.valueOf(bytesMessage.getByteProperty("userToken"));  //用户Token
                imageData.put("userToken", userToken);
                String dataType = bytesMessage.getStringProperty("dataType");
                if(dataType.equals("prepare")){ //判断消息类型，"prepare"代表传送的是动作ID信息
                    imageData.put("dataType", dataType);
                    String poseID = bytesMessage.getStringProperty("poseID");
                    imageData.put("poseID", poseID);
                    JSONObject jsonObject = JSONObject.fromObject(imageData);
                    stringRedisTemplate.convertAndSend("MatchingInputChannel", jsonObject.toString());
                }else if(dataType.equals("end")){
                    imageData.put("dataType", dataType);
                    JSONObject jsonObject = JSONObject.fromObject(imageData);
                    stringRedisTemplate.convertAndSend("MatchingInputChannel", jsonObject.toString());
                }else{ //否则收到的是图片信息
                    String base64Image =""; //原始图像
                    byte[] buffer = new byte[1024*1024];
                    int len = 0;
                    while((len = bytesMessage.readBytes(buffer)) != -1){
                        base64Image = new String(buffer,0, len);
                    }
                    imageData.put("image", base64Image);
                    JSONObject jsonObject = JSONObject.fromObject(imageData);
                    stringRedisTemplate.convertAndSend("PoseRawInputChannel", jsonObject.toString()); //发布内容，注意通道名称一致
                }
                //手动释放资源，不然会因为jedisPool里面的maxActive=200的限制，只能创建200个jedis资源。
                jedis.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    @JmsListener(destination = "riskBehaviourInput", containerFactory = "queueListenerFactory")
    public void riskBehaviourOnMessage(Message message) {
        if (message instanceof BytesMessage) {
            BytesMessage bytesMessage = (BytesMessage)message;
            try {
                String base64Image ="";                                            //原始图像
                byte[] buffer = new byte[1024*1024];
                int len = 0;
                while((len = bytesMessage.readBytes(buffer)) != -1){
                    base64Image = new String(buffer,0, len);
                }
                Jedis jedis = jedisPool.getResource();

                Map<String,String> imageData = new HashMap<String, String>();
                imageData.put("image", base64Image);

                JSONObject jsonObject = JSONObject.fromObject(imageData);
                stringRedisTemplate.convertAndSend("RiskBehaviourInput", jsonObject.toString()); //发布内容，注意通道名称一致

                //手动释放资源，不然会因为jedisPool里面的maxActive=200的限制，只能创建200个jedis资源。
                jedis.close();

            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
