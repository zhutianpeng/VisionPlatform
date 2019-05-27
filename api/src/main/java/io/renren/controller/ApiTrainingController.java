package io.renren.controller;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.R;
import io.renren.service.TrainingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @PostMapping("getTraining")
    @ApiOperation("获取所有训练计划")
    @Transactional
    public R getTraining(@RequestBody JSONObject data){
        Long userId = data.getLong("userId");
        return R.ok().put("map", trainingService.getTraining(userId));
    }

}
