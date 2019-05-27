package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

@Data
@TableName("jointmotioninstance")
public class MotionInstanceEntity {

    /**
     * 训练关节ID
     */
    @TableId
    private Long jointmotioninstanceId;
    /**
     * 训练动作ID
     */
    private Long movetypeinstanceId;
    /**
     * 训练关节名称
     */
    private String jointmotionName;
    /**
     * 训练关节角度
     */
    private Float rotateAngle;
    /**
     * 骨骼1
     */
    private Long bone1;
    /**
     * 骨骼2
     */
    private Long bone2;
    /**
     * 标志位
     */
    private String sign;

}
