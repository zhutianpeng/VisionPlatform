package io.renren.fileCenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.fileCenter.dao.UserPoseDao;
import io.renren.fileCenter.entity.UserPose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userPoseService")
public class UserPoseServiceImpl extends ServiceImpl<UserPoseDao, UserPose> {

    public void savePose(){
        UserPose userPose = new UserPose();
        userPose.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        String pose = "pose example";
        userPose.setPose(pose);
        this.saveOrUpdate(userPose);
    }

    public UserPose queryPose(String userToken){
        return baseMapper.selectOne(new QueryWrapper<UserPose>().eq("userToken", userToken));
    }
}
