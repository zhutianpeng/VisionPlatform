package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

@Data
@TableName("moveset")
public class MoveSetEntity {

    /**
     * 训练计划ID
     */
    @TableId
    private Long movesetId;
    /**
     * 训练计划ID
     */
    private Long trainingId;
    /**
     * 训练计划名称
     */
    private String name;

}
