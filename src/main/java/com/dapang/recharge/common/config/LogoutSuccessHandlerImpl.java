package com.dapang.recharge.common.config;

import com.alibaba.fastjson2.JSON;
import com.dapang.recharge.common.pojo.Result;
import com.dapang.recharge.common.util.JwtTokenUtil;
import com.dapang.recharge.pojo.po.LoginUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liangjy
 * @Description 退出登录
 * @createTime 2022/10/18
 */
@Slf4j
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        LoginUserDetails userDetails = jwtTokenUtil.getLoginUserByRequest(request);

        if (userDetails != null) {
            redisTemplate.delete("LOGIN:" + userDetails.getToken());
        }
        response.getWriter().println(JSON.toJSONString(Result.success("退出成功")));
        response.getWriter().flush();
    }
}
