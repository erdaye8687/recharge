package com.dapang.recharge.service;

import com.dapang.recharge.pojo.vo.AuthLoginRespVO;

/**
 * @author liangjy
 * @Description TODO
 * @createTime 2024/10/20
 */
public interface AdminAuthService {
    /**
     * 管理员登录
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    AuthLoginRespVO login(String username, String password);
}
