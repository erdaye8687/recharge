package com.dapang.recharge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dapang.recharge.pojo.po.UserPO;
import com.dapang.recharge.mapper.UserMapper;
import com.dapang.recharge.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author cainiao
 * @since 2024-10-20 09:14:25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserPO getUserByUsername(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<UserPO>().eq(UserPO::getUsername, username));
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
