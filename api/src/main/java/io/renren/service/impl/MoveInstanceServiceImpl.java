package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.MoveInstanceDao;
import io.renren.entity.MoveInstanceEntity;
import io.renren.service.MoveInstanceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

@Service("moveInstanceService")
public class MoveInstanceServiceImpl extends ServiceImpl<MoveInstanceDao, MoveInstanceEntity> implements MoveInstanceService {

    @Override
    public List<MoveInstanceEntity> queryByMoveSetId(Long moveSetId){
        return baseMapper.selectList(new QueryWrapper<MoveInstanceEntity>().eq("moveset_id", moveSetId));
    }

}
