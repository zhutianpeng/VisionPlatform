package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.entity.MotionInstanceEntity;

import java.util.List;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

public interface MotionInstanceService extends IService<MotionInstanceEntity> {

    List<MotionInstanceEntity> queryByMoveInstanceId(Long moveInstanceId);

}
