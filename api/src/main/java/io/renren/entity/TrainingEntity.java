package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

@Data
@TableName("trainingprogram")
public class TrainingEntity {

    /**
     * 训练计划ID
     */
    @TableId
    private Long trainingId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 训练计划名称
     */
    private String name;
    /**
     * 星期几训练
     */
    private String dayofweek;
    /**
     * 训练开始日期
     */
    private Date starttime;
    /**
     * 训练结束日期
     */
    private Date endtime;
    /**
     * 训练天数
     */
    private Long defaultdays;
    /**
     * 训练计划来源
     */
    private Long parent;
    /**
     * 医生ID
     */
    private Long docUserId;
    /**
     * 每天重复次数
     */
    private String repeatPerDay;
    /**
     * 病人可见
     */
    private Integer visibility;
    /**
     * 训练动作
     */
    @TableField(exist = false)
    private List<MoveInstanceEntity> moveInstanceList;

}
