package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

@Data
@TableName("moveinstance")
public class MoveInstanceEntity {

    /**
     * 训练动作ID
     */
    @TableId
    private Long moveinstanceId;
    /**
     * 动作库名称
     */
    private String movetypeName;
    /**
     * 训练计划ID
     */
    private Long movesetId;
    /**
     * 动作库ID
     */
    private Long movetypeId;
    /**
     * 一次几组
     */
    private Long groupsPerRepeat;
    /**
     * 一轮几次
     */
    private Long timesPerGroup;
    /**
     * 每轮休息
     */
    private Long restBetweenGroup;
    /**
     * 每组节奏
     */
    private String rhythm;
    /**
     * 训练动作顺序
     */
    private Long sequence;
    /**
     * 暂定—动作数据
     */
    private String leftorright;

}
