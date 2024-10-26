package com.dapang.recharge.pojo.po;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author cainiao
 * @since 2024-10-20 09:14:25
 */
@Data
public class UserPO extends BasePO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 用户角色（代理/供货商/运营）
     */
    private Integer role;

    /**
     * 上级代理ID
     */
    private Long parentId;

    /**
     * 用户余额
     */
    private BigDecimal balance;

    /**
     * 代理提成比例
     */
    private BigDecimal commissionRate;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户状态（active/inactive）
     */
    private Integer status;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
}
