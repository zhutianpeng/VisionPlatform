package io.renren.imgProcess.service.redisService;

import com.alibaba.fastjson.JSONObject;

import io.renren.fileCenter.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

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
        String local_dir = "K:\\workplace\\fileUpload\\";

        if(fileService.downloadToFtp(file_dir,file_name,local_dir)){
            System.out.println("文件下载成功！");
        }
    }


}
