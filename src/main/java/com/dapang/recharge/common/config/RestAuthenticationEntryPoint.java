package com.dapang.recharge.common.config;


import com.alibaba.fastjson2.JSON;
import com.dapang.recharge.common.pojo.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liangjy
 * @Description 身份认证失败或token失效
 * @createTime 2022/10/18
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSON.toJSONString(Result.error(101, "登录凭证失效，请重新登录！")));
        response.getWriter().flush();
    }
}
