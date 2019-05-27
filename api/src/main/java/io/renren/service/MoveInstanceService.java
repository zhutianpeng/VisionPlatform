package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.entity.MoveInstanceEntity;

import java.util.List;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

public interface MoveInstanceService extends IService<MoveInstanceEntity> {

    List<MoveInstanceEntity> queryByMoveSetId(Long moveSetId);

}
