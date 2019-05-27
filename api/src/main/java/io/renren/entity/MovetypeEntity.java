package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: Song
 * @Date: 2019/5/27 17:00
 */

@Data
@TableName("movetype")
public class MovetypeEntity {

    /**
     * 动作库ID
     */
    @TableId
    private Long movetypeId;
    /**
     * 动作库名称
     */
    private String movetypeName;
    /**
     * 动作库缩略图
     */
    private String imgpath;
    /**
     * 动作库方向
     */
    private Integer sideexist;
    /**
     * 动作库前向图
     */
    private String frontpath;
    /**
     * 动作库侧向图
     */
    private String sidepath;
    /**
     * 动作库视频
     */
    private String videopath;
    /**
     * 动作库app图
     */
    private String imgforapp;
    /**
     * 动作库骨骼信息
     */
    private String composition;
    /**
     * 动作库描述
     */
    private String instructionPic;
    /**
     * 视图
     */
    private Integer modelView;
}
