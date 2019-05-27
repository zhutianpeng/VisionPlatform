package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.MoveInstanceDao;
import io.renren.entity.MoveInstanceEntity;
import io.renren.service.MotionInstanceService;
import io.renren.service.MoveInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

@Service("moveInstanceService")
public class MoveInstanceServiceImpl extends ServiceImpl<MoveInstanceDao, MoveInstanceEntity> implements MoveInstanceService {

    @Autowired
    private MotionInstanceService motionInstanceService;

    @Override
    public List<MoveInstanceEntity> getMove(Long moveSetId){
        List<MoveInstanceEntity> moveInstanceList = baseMapper.selectList(new QueryWrapper<MoveInstanceEntity>().eq("moveset_id", moveSetId));
        for(MoveInstanceEntity moveInstance:moveInstanceList){
            moveInstance.setMotionInstanceList(motionInstanceService.getMotion(moveInstance.getMoveinstanceId()));
        }
        return moveInstanceList;
    }

}
