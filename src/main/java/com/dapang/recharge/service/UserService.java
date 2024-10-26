package com.dapang.recharge.service;

import com.dapang.recharge.pojo.po.UserPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author cainiao
 * @since 2024-10-20 09:14:25
 */
public interface UserService extends IService<UserPO> {

    UserPO getUserByUsername(String username);

    /**
     * 判断密码是否匹配
     *
     * @param rawPassword 未加密的密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    boolean isPasswordMatch(String rawPassword, String encodedPassword);
}
