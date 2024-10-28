package com.dapang.recharge.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.dapang.recharge.common.enums.CommonStatusEnum;
import com.dapang.recharge.common.enums.LoginLogTypeEnum;
import com.dapang.recharge.common.util.JwtTokenUtil;
import com.dapang.recharge.pojo.po.LoginUserDetails;
import com.dapang.recharge.pojo.po.UserPO;
import com.dapang.recharge.pojo.vo.AuthLoginRespVO;
import com.dapang.recharge.pojo.vo.UserSaveReqVO;
import com.dapang.recharge.service.AdminAuthService;
import com.dapang.recharge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.dapang.recharge.common.constant.ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS;
import static com.dapang.recharge.common.constant.ErrorCodeConstants.AUTH_LOGIN_USER_DISABLED;
import static com.dapang.recharge.common.exception.util.ServiceExceptionUtil.exception;

/**
 * @author liangjy
 * @Description TODO
 * @createTime 2024/10/20
 */
@Service
public class AdminAuthServiceImpl implements AdminAuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthLoginRespVO login(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
        Authentication authenticate;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            authenticate = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        }catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
            } else {
                throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
            }
        } finally {
            SecurityContextHolder.clearContext();
        }
        LoginUserDetails userDetails = (LoginUserDetails) authenticate.getPrincipal();
        UserPO user = userDetails.getUserPO();
        // 校验是否禁用
        if (CommonStatusEnum.isDisable(user.getStatus())) {
//            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        String token = jwtTokenUtil.generateToken(userDetails);
        return new AuthLoginRespVO().setUserId(user.getId()).setUserType(user.getRole()).setRefreshToken(token);
    }

    @Override
    public Long createUser(UserSaveReqVO reqVO) {
        UserPO userPO = BeanUtil.toBean(reqVO, UserPO.class);
        userPO.setPassword(passwordEncoder.encode(reqVO.getPassword()));
        userService.save(userPO);
        return userPO.getId();
    }
}
