package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.MoveInstanceDao;
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

@Service("moveInstanceService")
public class MoveInstanceServiceImpl extends ServiceImpl<MoveInstanceDao, MoveInstanceEntity> implements MoveInstanceService {

    @Autowired
    private MovetypeService movetypeService;

    @Autowired
    private MotionInstanceService motionInstanceService;

    @Getter
    @Setter
    public class move{
        private Long moveInstanceId;
        private String movetypeName;
        private Long groupsPerRepeat;
        private Long timesPerGroup;
        private Long restBetweenGroup;
        private String rhythm;
        private String moveImage;
        private List motionList;

        public move(Long moveInstanceId, String movetypeName, Long groupsPerRepeat, Long timesPerGroup, Long restBetweenGroup, String rhythm, String moveImage, List motionList){
            this.moveInstanceId = moveInstanceId;
            this.movetypeName = movetypeName;
            this.groupsPerRepeat = groupsPerRepeat;
            this.timesPerGroup = timesPerGroup;
            this.restBetweenGroup = restBetweenGroup;
            this.rhythm = rhythm;
            this.moveImage = moveImage;
            this.motionList = motionList;
        }

    }

    @Override
    public MoveInstanceEntity getMoveInstance(Long moveinstanceId){
        return baseMapper.selectOne(new QueryWrapper<MoveInstanceEntity>().eq("moveinstance_id", moveinstanceId));
    }

    @Override
    public List getMove(Long moveSetId){
        List<MoveInstanceEntity> moveInstanceList = baseMapper.selectList(new QueryWrapper<MoveInstanceEntity>().eq("moveset_id", moveSetId));
        List<move> moveList = new ArrayList<>();
        for(MoveInstanceEntity moveInstance:moveInstanceList){
            MovetypeEntity movetype = movetypeService.getMovetype(moveInstance.getMovetypeId());
            move move = new move(moveInstance.getMoveinstanceId(), moveInstance.getMovetypeName(), moveInstance.getGroupsPerRepeat(), moveInstance.getTimesPerGroup(), moveInstance.getRestBetweenGroup(), moveInstance.getRhythm(), movetype.getImgpath(), motionInstanceService.getMotion(moveInstance.getMoveinstanceId()));
            moveList.add(move);
        }
        return moveList;
    }

}
