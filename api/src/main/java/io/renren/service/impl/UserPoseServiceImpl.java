package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.UserPoseDao;
import io.renren.entity.UserPoseEntity;
import io.renren.service.UserPoseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("userPoseService")
public class UserPoseServiceImpl extends ServiceImpl<UserPoseDao, UserPoseEntity> implements UserPoseService {

    @Override
    public void savePose(){
        UserPoseEntity userPoseEntity = new UserPoseEntity();
        String pose = "pose example";
        userPoseEntity.setPoseData(pose);
        this.saveOrUpdate(userPoseEntity);
    }

    @Override
    public UserPoseEntity queryPose(String userToken){
        List<UserPoseEntity> userPoseList = baseMapper.selectList(new QueryWrapper<UserPoseEntity>().eq("user_token", userToken));
        return userPoseList.get(userPoseList.size() - 1); //获取最新的一组数据
    }
}
