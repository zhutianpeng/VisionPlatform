package io.renren.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author: Song
 * @Date: 2019/5/27 10:00
 */

@Data
@ApiModel(value = "登录表单")
public class LoginForm {
    @ApiModelProperty(value = "用户名")
    @NotBlank(message="用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message="密码不能为空")
    private String password;

}
