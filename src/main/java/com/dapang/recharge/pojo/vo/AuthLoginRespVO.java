package com.dapang.recharge.pojo.vo;

import com.dapang.recharge.common.enums.UserTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liangjy
 * @Description TODO
 * @createTime 2024/10/20
 */
@Data
@Accessors(chain = true)
public class AuthLoginRespVO {
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     *
     * 枚举 {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;
}
