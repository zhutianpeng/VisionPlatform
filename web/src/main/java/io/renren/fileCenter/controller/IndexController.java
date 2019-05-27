package io.renren.fileCenter.controller;

import io.renren.common.utils.R;
import io.renren.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @Autowired
    private TrainingService trainingService;

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    @RequestMapping("/offLine3D")
    public String offLine3D(){
        return "exercise3D";
    }

    @RequestMapping("/free")
    public String free(){
        return "free";
    }

    @RequestMapping("/realTime2D")
    public String realTime2D(){
        return "exercise2D";
    }

//    @RequestMapping("/getTrainingPlan")
//    public R getTraining(@RequestBody Long userId){
//        return R.ok().put("map", trainingService.getTraining(userId));
//    }
}
