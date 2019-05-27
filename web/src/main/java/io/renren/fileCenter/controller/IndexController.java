package io.renren.fileCenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
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
}
