package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.TrainingDao;
import io.renren.entity.MoveSetEntity;
import io.renren.entity.TrainingEntity;
import io.renren.service.MoveInstanceService;
import io.renren.service.MoveSetService;
import io.renren.service.TrainingService;
import io.renren.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

@Service("trainingService")
public class TrainingServiceImpl extends ServiceImpl<TrainingDao, TrainingEntity> implements TrainingService {

    @Autowired
    private UserService userService;

    @Autowired
    private MoveSetService moveSetService;

    @Autowired
    private MoveInstanceService moveInstanceService;

    @Getter
    @Setter
    public class training{
        private Long trainingId;
        private String name;
        private String dayofweek;
        private Date starttime;
        private Date endtime;
        private String docUsername;
        private String repeatPerDay;
        private List moveList;

        public training(Long trainingId, String name, String dayofweek, Date starttime, Date endtime, String docUsername, String repeatPerDay, List moveList){
            this.trainingId = trainingId;
            this.name = name;
            this.dayofweek =dayofweek;
            this.starttime = starttime;
            this.endtime = endtime;
            this.docUsername = docUsername;
            this.repeatPerDay = repeatPerDay;
            this.moveList = moveList;
        }

    }

    @Override
    public List getTraining(Long userId){
        List<TrainingEntity> trainingEntityList = baseMapper.selectList(new QueryWrapper<TrainingEntity>().eq("user_id", userId));
        List<training> trainingList = new ArrayList<>();
        for(TrainingEntity trainingEntity:trainingEntityList){
            MoveSetEntity moveSet = moveSetService.getMoveSet(trainingEntity.getTrainingId());
            training training = new training(trainingEntity.getTrainingId(), trainingEntity.getName(), trainingEntity.getDayofweek(), trainingEntity.getStarttime(), trainingEntity.getEndtime(), userService.queryByUserId(trainingEntity.getDocUserId()).getUsername(), trainingEntity.getRepeatPerDay(), moveInstanceService.getMove(moveSet.getMovesetId()));
            trainingList.add(training);
        }
        return trainingList;
    }

}
