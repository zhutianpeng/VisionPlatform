package io.renren.controller;

import com.alibaba.fastjson.JSONObject;
import io.renren.annotation.Login;
import io.renren.common.utils.R;
import io.renren.service.TrainingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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

    @Login
    @GetMapping("getTrainingFromToken")
    @ApiOperation("token获取所有训练计划")
    public R getTrainingFromToken(@ApiIgnore @RequestAttribute("userId") Long userId){
        return R.ok().put("trainingList", trainingService.getTraining(userId));
    }

    @PostMapping("getTrainingFromUserId")
    @ApiOperation("userId获取所有训练计划")
    public R getTrainingFromUserId(Long userId){
        return R.ok().put("trainingList", trainingService.getTraining(userId));
    }

    @PostMapping("getTrainingFromJson")
    @ApiOperation("json获取所有训练计划")
    @Transactional
    public R getTrainingFromJson(@RequestBody JSONObject data){
        Long userId = data.getLong("userId");
        return R.ok().put("trainingList", trainingService.getTraining(userId));
    }

}
