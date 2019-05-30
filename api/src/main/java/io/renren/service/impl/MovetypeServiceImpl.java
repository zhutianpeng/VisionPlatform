package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.MovetypeDao;
import io.renren.entity.MovetypeEntity;
import io.renren.service.MovetypeService;
import org.springframework.stereotype.Service;

/**
 * @Author: Song
 * @Date: 2019/5/27 17:00
 */

@Service("movetypeService")
public class MovetypeServiceImpl extends ServiceImpl<MovetypeDao, MovetypeEntity> implements MovetypeService {

    @Override
    public MovetypeEntity getMovetype(Long movetypeId){
        return baseMapper.selectOne(new QueryWrapper<MovetypeEntity>().eq("movetype_id", movetypeId));
    }

}
