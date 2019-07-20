package io.renren.fileCenter.controller;

import io.renren.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

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

    @RequestMapping("/test")
    public String test3D(){
        return "test3D";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
