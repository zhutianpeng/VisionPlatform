package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.entity.UserEntity;
import io.renren.form.LoginForm;

import java.util.Map;

/**
 * @Author: Song
 * @Date: 2019/5/27 10:00
 */

public interface UserService extends IService<UserEntity> {

	UserEntity queryByUsername(String username);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回登录信息
	 */
	Map<String, Object> login(LoginForm form);
}
