package com.czscloud.czs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@ApiModel(value="用户表", description="用户表")
public class User {
    @Data
    @ApiModel(value="用户表", description="用户表")
    public class OauthUser implements Serializable {

        @ApiModelProperty(value = "用户的主键ID")
        @TableId(type = IdType.AUTO)
        private Long userId;

        @ApiModelProperty(value = "用户名称")
        private String userName;

        @ApiModelProperty(value = "手机号")
        private String userPhone;

        @ApiModelProperty(value = "地址")
        private String userAddress;

        @ApiModelProperty(value = "是否启用")
        private Boolean userEnabled;

        @ApiModelProperty(value = "账号")
        private String userNumber;

        @ApiModelProperty(value = "密码")
        private String userPassword;

        @ApiModelProperty(value = "描述")
        private String userRemark;

        @ApiModelProperty(value = "微信登陆获取的信息")
        private String userOpenid;

        @ApiModelProperty(value = "值为1时是男性，值为2时是女性，值为0时是未知")
        private Integer userSex;

        @ApiModelProperty(value = "用户生日")
        private LocalDateTime userBirthDay;

        @ApiModelProperty(value = "省份")
        private String userProvince;

        @ApiModelProperty(value = "城市")
        private String userCity;

        @ApiModelProperty(value = "头像")
        private String userHeadImage;

        @ApiModelProperty(value = "用户创建时间")
        private LocalDateTime userCreateTime;

        @TableField(exist = false)
        @ApiModelProperty(value = "用户拥有的权限列表")
        private Set<GrantedAuthority> authorities;

    }
}
