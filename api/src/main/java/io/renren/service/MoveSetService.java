package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.entity.MoveSetEntity;

import java.util.List;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

public interface MoveSetService extends IService<MoveSetEntity> {

    MoveSetEntity getMoveSet(Long trainingId);

}
