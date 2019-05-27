package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.entity.TrainingEntity;

import java.util.List;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

public interface TrainingService extends IService<TrainingEntity> {

    List<TrainingEntity> getTraining(Long userId);

}
