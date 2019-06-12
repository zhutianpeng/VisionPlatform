package io.renren.imgProcess.service.activemqService;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ActiveMQListener {

    @Autowired
    private JedisPool jedisPool;

    @JmsListener(destination = "video", containerFactory = "queueListenerFactory")
    public void videoOnMessage(Message message) {
        if(message instanceof TextMessage){
            TextMessage textMsg = (TextMessage) message;
            System.out.println("接收到一个纯文本消息");
            try {
                System.out.println("消息内容是：" + textMsg.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
//      此处才是正常接受的数据类型
        }else if (message instanceof BytesMessage) {
            BytesMessage bytesMessage = (BytesMessage)message;
            try {
                // 得到一些参数：

                String userToken = String.valueOf(bytesMessage.getByteProperty("user-token"));  //用户Token
                String task = String.valueOf(bytesMessage.getByteProperty("task"));               //执行的哪一个Task
                String currentTime = String.valueOf(bytesMessage.getLongProperty("time")); //当前时间
                String moveId = String.valueOf(bytesMessage.getIntProperty("moveId"));
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
                imageData.put("currentTime", currentTime);
                imageData.put("moveId", moveId);
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

}
