package io.renren.job.fileCenter.controller;

import io.renren.job.fileCenter.entity.User;
import io.renren.job.fileCenter.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@RestController
@Api(tags="文件上传")
public class FileFtpController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private FileService fileService;

    @Autowired
    private User user;

    @ApiOperation("上传")
    @RequestMapping(value = "/ftpUpload",method = RequestMethod.POST ,consumes = "multipart/form-data")
    public void uploadFile(@RequestParam(name = "fileName") MultipartFile multipartFile)
    {
        //指定存放上传文件的目录
        String fileDir = "K:\\workplace\\fileUpload";
        File dir = new File(fileDir);

        //判断目录是否存在，不存在则创建目录
        if (!dir.exists()){
            dir.mkdirs();
        }

        //生成新文件名，防止文件名重复而导致文件覆盖
        String fileName = multipartFile.getOriginalFilename();
        fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
        String filePath = user.getBasepath() +'/'+ fileName;

        HashMap<String,String> fileData = new HashMap<String, String>();
        fileData.put("filePath",filePath);
        JSONObject jsonObject = JSONObject.fromObject(fileData);

        File file = new File(dir, fileName);

        //传输内容
        try {
            multipartFile.transferTo(file);
            System.out.println("上传文件成功！");
        } catch (IOException e) {
            System.out.println("上传文件失败！");
            e.printStackTrace();
        }

        //至此，文件已经传到了程序运行的服务器上。

        //ftp方式上传至服务器
             //1、上传文件
        if (fileService.uploadToFtp(file)){
            redisTemplate.convertAndSend("fileChannel",jsonObject.toString());
            System.out.println("上传至服务器！");
        }else {
            System.out.println("上传至服务器失败!");
        }

    }


    @ApiOperation("下载")
    @RequestMapping(value = "/ftpDownload",method = RequestMethod.GET)
    public void downloadFile(){
        String file_path = "/home/messor/users/zhutianpeng/oldFile/tf-pose-estimation-ztp/etcs/VideoOut/result/20190523164253_pose.txt";
        String fName = file_path.trim();
        String fileName = fName.substring(fName.lastIndexOf("/")+1);
        String file_dir = fName.substring(0,fName.lastIndexOf("/")+1);
        String local_dir = "K:\\workplace\\fileUpload\\";
        fileService.downloadToFtp(file_dir,fileName,local_dir);
    }


}
