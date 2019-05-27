package io.renren.fileCenter.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserPoseController {

    @RequestMapping(value = "/getUserPose", method = RequestMethod.POST)
    public JSONObject getUserPose(){
        JSONObject result = new JSONObject();
        return result;
    }
}
