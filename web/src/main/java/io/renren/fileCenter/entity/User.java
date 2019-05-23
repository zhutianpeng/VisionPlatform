package io.renren.fileCenter.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class User {
    //ftp服务器ip地址
    private String address = "10.103.238.165";
    //端口号
    private int port = 22;
    //用户名
    private String username = "gxk";
    //密码
    private String password = "gaoxinkai";
    //图片路径
    private String basepath = "/mnt/data-1/gxk";
}
