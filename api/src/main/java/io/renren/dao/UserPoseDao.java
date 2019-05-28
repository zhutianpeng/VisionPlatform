/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.renren.entity.UserPoseEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户离线3D姿态数据
 *
 * @author Fishhao123
 */
@Mapper
public interface UserPoseDao extends BaseMapper<UserPoseEntity> {

}
