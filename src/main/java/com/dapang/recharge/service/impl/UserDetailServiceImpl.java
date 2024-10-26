package com.dapang.recharge.service.impl;

import com.alibaba.fastjson2.JSON;
import com.dapang.recharge.pojo.po.LoginUserDetails;
import com.dapang.recharge.pojo.po.UserPO;
import com.dapang.recharge.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author liangjy
 * @Description TODO
 * @createTime 2022/10/19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            log.warn("用户登陆用户名为空:{}", username);
            throw new UsernameNotFoundException("用户名不能为空");
        }
        UserPO user = userService.getUserByUsername(username);
        if (user == null) {
            log.warn("根据用户名没有查找到用户:{}", username);
        }
        log.info("根据用户名:{}获取用户登陆信息:{}", username, JSON.toJSONString(user));

        LoginUserDetails userDetails = new LoginUserDetails(user);
        return userDetails;
    }
}
