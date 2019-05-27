package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.TrainingDao;
import io.renren.entity.MoveSetEntity;
import io.renren.entity.TrainingEntity;
import io.renren.service.MoveInstanceService;
import io.renren.service.MoveSetService;
import io.renren.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

@Service("trainingService")
public class TrainingServiceImpl extends ServiceImpl<TrainingDao, TrainingEntity> implements TrainingService {

    @Autowired
    private MoveSetService moveSetService;

    @Autowired
    private MoveInstanceService moveInstanceService;

    @Override
    public List<TrainingEntity> getTraining(Long userId){
        List<TrainingEntity> trainingList = baseMapper.selectList(new QueryWrapper<TrainingEntity>().eq("user_id", userId));
        for(TrainingEntity training:trainingList){
            MoveSetEntity moveSet = moveSetService.getMoveSet(training.getTrainingId());
            training.setMoveInstanceList(moveInstanceService.getMove(moveSet.getMovesetId()));
        }
        return trainingList;
    }

}
