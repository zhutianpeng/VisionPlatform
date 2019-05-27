package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.TrainingDao;
import io.renren.entity.TrainingEntity;
import io.renren.service.TrainingService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

@Service("trainingService")
public class TrainingServiceImpl extends ServiceImpl<TrainingDao, TrainingEntity> implements TrainingService {

    @Override
    public List<TrainingEntity> queryByUserId(Long userId){
        return baseMapper.selectList(new QueryWrapper<TrainingEntity>().eq("user_id", userId));
    }

}
