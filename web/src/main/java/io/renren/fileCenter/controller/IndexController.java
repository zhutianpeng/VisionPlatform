package io.renren.fileCenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

//    离线3D姿态估计
    @RequestMapping("/offline3D")
    public String offLine3D(){
        return "offline3D";
    }

    //    离线2D姿态估计
    @RequestMapping("/offline2D")
    public String offLine2D(){
        return "offline2D";
    }

    @RequestMapping("/free")
    public String free(){
        return "free";
    }

    @RequestMapping("/realTimeExercise")
    public String realTimeExercise(){
        return "exercise";
    }

    @RequestMapping("/test")
    public String test3D(){
        return "test3D";
    }

    @RequestMapping("/demo")
    public String demo(){
        return "demo";
    }


    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/mkgame")
    public String mkgame(){
        return "mkgame";
    }

    @RequestMapping("/riskBehaviourDetection")
    public String riskBehaviourDetection(){
        return "riskBehaviour";
    }
}
