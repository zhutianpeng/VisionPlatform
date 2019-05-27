package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.entity.MovetypeEntity;

/**
 * @Author: Song
 * @Date: 2019/5/27 17:00
 */

public interface MovetypeService extends IService<MovetypeEntity> {

    MovetypeEntity getMovetype(Long movetypeId);
}
