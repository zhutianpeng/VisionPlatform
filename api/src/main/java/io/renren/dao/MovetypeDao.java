package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.MovetypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: Song
 * @Date: 2019/5/27 17:00
 */

@Mapper
@Repository
public interface MovetypeDao extends BaseMapper<MovetypeEntity> {

}
