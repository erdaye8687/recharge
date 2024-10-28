package com.dapang.recharge.service;

import com.dapang.recharge.pojo.vo.AuthLoginRespVO;
import com.dapang.recharge.pojo.vo.UserSaveReqVO;

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

    /**
     * 创建用户
     *
     * @param reqVO 用户信息
     * @return 用户信息
     */
    Long createUser(UserSaveReqVO reqVO);
}
