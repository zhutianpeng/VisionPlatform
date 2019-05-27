package io.renren.fileCenter.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName("user_pose")
public class UserPose implements Serializable {

    @TableId(value = "id")
    private String Id;

    private String userToken;

    private String pose;

    private Timestamp createTime;


}
