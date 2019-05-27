package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.MotionInstanceDao;
import io.renren.entity.MotionInstanceEntity;
import io.renren.service.MotionInstanceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

@Service("motionInstanceService")
public class MotionInstanceServiceImpl extends ServiceImpl<MotionInstanceDao, MotionInstanceEntity> implements MotionInstanceService {

    @Override
    public List<MotionInstanceEntity> getMotion(Long moveInstanceId){
        return baseMapper.selectList(new QueryWrapper<MotionInstanceEntity>().eq("movetypeinstance_id", moveInstanceId));
    }

}
