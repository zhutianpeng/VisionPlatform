package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.UserPoseDao;
import io.renren.entity.UserPoseEntity;
import io.renren.service.UserPoseService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userPoseService")
public class UserPoseServiceImpl extends ServiceImpl<UserPoseDao, UserPoseEntity> implements UserPoseService {

    @Override
    public void savePose(){
        UserPoseEntity userPoseEntity = new UserPoseEntity();
        userPoseEntity.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        String pose = "pose example";
        userPoseEntity.setPose(pose);
        this.saveOrUpdate(userPoseEntity);
    }

    @Override
    public UserPoseEntity queryPose(String userToken){
        return baseMapper.selectOne(new QueryWrapper<UserPoseEntity>().eq("userToken", userToken));
    }
}
