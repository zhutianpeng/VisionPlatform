package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.MotionInstanceDao;
import io.renren.entity.MotionInstanceEntity;
import io.renren.entity.MoveInstanceEntity;
import io.renren.entity.MovetypeEntity;
import io.renren.service.MotionInstanceService;
import io.renren.service.MoveInstanceService;
import io.renren.service.MovetypeService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

@Service("motionInstanceService")
public class MotionInstanceServiceImpl extends ServiceImpl<MotionInstanceDao, MotionInstanceEntity> implements MotionInstanceService {

    @Autowired
    private MovetypeService movetypeService;

    @Autowired
    private MoveInstanceService moveInstanceService;

    @Getter
    @Setter
    public class motion{
        private Float rotateAngle;
        private String motionImage;

        public motion(Float rotateAngle, String motionImage){
            this.rotateAngle = rotateAngle;
            this.motionImage = motionImage;
        }

    }

    @Override
    public List getMotion(Long moveInstanceId){
        List<MotionInstanceEntity> motionInstanceList = baseMapper.selectList(new QueryWrapper<MotionInstanceEntity>().eq("movetypeinstance_id", moveInstanceId));
        List<motion> motionList = new ArrayList<>();
        for(MotionInstanceEntity motionInstance:motionInstanceList){
            MovetypeEntity movetype = movetypeService.getMovetype(moveInstanceService.getMoveInstance(motionInstance.getMovetypeinstanceId()).getMovetypeId());
            motion motion = new motion(motionInstance.getRotateAngle(), movetype.getInstructionPic());
            motionList.add(motion);
        }
        return motionList;
    }

}
