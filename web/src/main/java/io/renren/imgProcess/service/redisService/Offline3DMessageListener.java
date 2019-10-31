package io.renren.imgProcess.service.redisService;

import com.alibaba.fastjson.JSONObject;
import io.renren.fileCenter.controller.WebSocketController;
import io.renren.fileCenter.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Offline3DMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        JSONObject result = JSONObject.parseObject(message.toString());
        boolean flag = result.getBoolean("flag");
        String userToken = result.getString("userToken");
        if(flag){
            try {
                WebSocketController.sendInfo("File Available", userToken); //离线3D估计使用WebSocket传送数据
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("WebSocket发送失败！");
            }
        }
//
//        1. 入库，ws， 前端主动下载（可以循环播放）；下载
//
//        2. ws（通知+数据）（只能发一次）

    }
}
