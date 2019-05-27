package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.MoveSetEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: Song
 * @Date: 2019/5/27 03:00
 */

@Mapper
@Repository
public interface MoveSetDao extends BaseMapper<MoveSetEntity> {

}
