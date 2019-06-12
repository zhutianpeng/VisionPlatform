package io.renren.controller;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.R;
import io.renren.service.UserPoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserPoseController {

    @Autowired
    private UserPoseService userPoseService;


    @RequestMapping(value = "/getUserPose/{userToken}", method = RequestMethod.POST)
    public R getTraining(@PathVariable(value = "userToken") String userToken){
        return R.ok().put("userPose", userPoseService.queryPose(userToken));
    }

}
