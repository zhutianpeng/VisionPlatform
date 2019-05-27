package io.renren.controller;

import io.renren.annotation.Login;
import io.renren.common.utils.R;
import io.renren.entity.MotionInstanceEntity;
import io.renren.entity.MoveInstanceEntity;
import io.renren.entity.MoveSetEntity;
import io.renren.entity.TrainingEntity;
import io.renren.service.MotionInstanceService;
import io.renren.service.MoveInstanceService;
import io.renren.service.MoveSetService;
import io.renren.service.TrainingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

@RestController
@RequestMapping("/api")
@Api(tags = "查询接口")
public class ApiTrainingController {

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private MoveSetService moveSetService;

    @Autowired
    private MoveInstanceService moveInstanceService;

    @Autowired
    private MotionInstanceService motionInstanceService;

    @Login
    @GetMapping("training")
    @ApiOperation("获取所有训练计划")
    @Transactional
    public R userTraining(@ApiIgnore @RequestAttribute("userId") Long userId){
        List<TrainingEntity> trainingList = trainingService.queryByUserId(userId);
        int i = 0;
        Map map = new LinkedHashMap();
        for(TrainingEntity training:trainingList){
            int j = 0;
            Map mapOne = new LinkedHashMap();
            mapOne.put("training", training);
            List<MoveSetEntity> moveSetList = moveSetService.queryByTrainingId(training.getTrainingId());
            for(MoveSetEntity moveSet:moveSetList){
                int k = 0;
                Map mapTwo = new LinkedHashMap();
                mapTwo.put("moveSet", moveSet);
                List<MoveInstanceEntity> moveInstanceList = moveInstanceService.queryByMoveSetId(moveSet.getMovesetId());
                for(MoveInstanceEntity moveInstance:moveInstanceList){
                    Map mapThree = new LinkedHashMap();
                    mapThree.put("moveInstance", moveInstance);
                    List<MotionInstanceEntity> motionInstanceList = motionInstanceService.queryByMoveInstanceId(moveInstance.getMoveinstanceId());
                    mapThree.put("motionInstance", motionInstanceList);
                    mapTwo.put("mapThree" + (k++), mapThree);
                }
                mapOne.put("mapTwo" + (j++), mapTwo);
            }
            map.put("mapOne" + (i++), mapOne);
        }
        return R.ok().put("map", map);
    }

}
