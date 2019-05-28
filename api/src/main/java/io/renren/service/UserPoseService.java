package io.renren.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.entity.UserPoseEntity;

public interface UserPoseService {

    public void savePose();

    public UserPoseEntity queryPose(String userToken);
}
