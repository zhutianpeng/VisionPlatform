package io.renren.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.common.validator.Assert;
import io.renren.dao.UserDao;
import io.renren.entity.TokenEntity;
import io.renren.entity.UserEntity;
import io.renren.form.LoginForm;
import io.renren.service.TokenService;
import io.renren.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Song
 * @Date: 2019/5/27 10:00
 */

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
	@Autowired
	private TokenService tokenService;

	@Override
	public UserEntity queryByUserId(Long userId){
		return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("user_id", userId));
	}

	@Override
	public UserEntity queryByUsername(String username) {
		return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", username));
	}

	@Override
	public Map<String, Object> login(LoginForm form) {
		UserEntity user = queryByUsername(form.getUsername());
		Assert.isNull(user, "用户名或密码错误");

		//密码错误
		if(!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))){
			throw new RRException("用户名或密码错误");
		}

		//获取登录token
		TokenEntity tokenEntity = tokenService.createToken(user.getUserId());

		Map<String, Object> map = new HashMap<>(2);
		map.put("token", tokenEntity.getToken());
		map.put("expire", tokenEntity.getExpireTime().getTime() - System.currentTimeMillis());

		return map;
	}

}
