package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName("file_data")
public class UserPoseEntity implements Serializable {

    @TableId(value = "id")
    private Integer id;

    private String userToken;

    private String filename;

    private Integer task;

    private String poseData;

    private Integer fps;

    private Timestamp createTime;


}
