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
public class FileMessageListener implements MessageListener {

    @Autowired
    private FileService fileService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        JSONObject json = JSONObject.parseObject(message.toString());
        String file_path = json.getString("resultPath");

        String fName = file_path.trim();
        String file_name = fName.substring(fName.lastIndexOf("/")+1);
        String file_dir = fName.substring(0,fName.lastIndexOf("/")+1);
//        Fixme:这里不能这样存，最好是做持久化,入库
        String local_dir = "K:\\workplace\\fileUpload\\";

        if(fileService.downloadToFtp(file_dir,file_name,local_dir)){
            System.out.println("文件下载成功！");
        }
//        TODO: ws+下载

        //TODO userToken
        try {
            WebSocketController.sendInfo("File Available", "fishhao123");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("WebSocket发送失败！");
        }


//
//        1. 入库，ws， 前端主动下载（可以循环播放）；下载
//
//        2. ws（通知+数据）（只能发一次）

    }


}
