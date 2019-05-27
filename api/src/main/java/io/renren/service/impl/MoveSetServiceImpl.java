package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.MoveSetDao;
import io.renren.entity.MoveSetEntity;
import io.renren.service.MoveSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

@Service("moveSetService")
public class MoveSetServiceImpl extends ServiceImpl<MoveSetDao, MoveSetEntity> implements MoveSetService {

    @Override
    public List<MoveSetEntity> queryByTrainingId(Long trainingId){
        return baseMapper.selectList(new QueryWrapper<MoveSetEntity>().eq("training_id", trainingId));
    }

}
