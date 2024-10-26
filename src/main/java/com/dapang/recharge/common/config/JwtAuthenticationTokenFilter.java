package com.dapang.recharge.common.config;

import com.alibaba.fastjson2.JSON;
import com.dapang.recharge.common.util.JwtTokenUtil;
import com.dapang.recharge.pojo.po.LoginUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liangjy
 * @Description JWT登录授权过滤器
 * @createTime 2022/10/18
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LoginUserDetails userDetails = jwtTokenUtil.getLoginUserByRequest(request);
//        CustomHttpServletRequest customHttpServletRequest = new CustomHttpServletRequest(request);
        if (userDetails != null) {
            log.info("authenticated user:{}", JSON.toJSONString(userDetails));
            jwtTokenUtil.validateToken(userDetails);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // 账号id放入请求头中
//            customHttpServletRequest.addHeader("puid", userDetails.getCdmAccountPO().getId().toString());
        }
        filterChain.doFilter(request, response);
    }
}
