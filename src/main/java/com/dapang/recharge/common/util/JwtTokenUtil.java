package com.dapang.recharge.common.util;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.dapang.recharge.pojo.po.LoginUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author liangjy
 * @createTime 2022/10/18
 *   JwtToken生成的工具类
 *   JWT token的格式：header.payload.signature
 *   header的格式（算法、token的类型）：
 *   {"alg": "HS512","typ": "JWT"}
 *   payload的格式（用户名、创建时间、生成时间）：
 *   {"sub":"wang","created":1489079981393,"exp":1489684781}
 *   signature的生成算法：
 *   HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 */
@Component
public class JwtTokenUtil {

    private Key secretKey;
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final Long MILLIS_MINUTE_TEN = 10 * 60 * 1000L;
    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";
    @Value("${token.secret}")
    private String secret;
    @Value("${token.expireTime}")
    private Long expireTime;
    @Value("${token.header}")
    private String tokenHeader;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostConstruct
    private void init() {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    /**
     * 根据负责生成JWT的token
     */
    private String generateToken(Map<String, Object> claims) {

        return Jwts.builder()
                .setClaims(claims)
//                .setExpiration(generateExpirationDate())
                .signWith(secretKey)
                .compact();
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOGGER.info("JWT格式验证失败:{},{}", token, e);
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expireTime * 1000);
    }

    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从request中获取登录用户名
     */
    public LoginUserDetails getLoginUserByRequest(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        if (StringUtils.isNotEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            token = token.replace(TOKEN_PREFIX, "");
            Claims claims = getClaimsFromToken(token);
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get(LOGIN_USER_KEY);
//            String userKey = getTokenKey(uuid);
            String loginUserStr = redisTemplate.opsForValue().get("LOGIN:" + uuid);
            LoginUserDetails loginUserDetails = JSON.parseObject(loginUserStr, LoginUserDetails.class);
            return loginUserDetails;
        }
        return null;
    }

    /**
     * 验证token是否还有效
     *
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken( LoginUserDetails userDetails) {
        long expireTime = userDetails.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshHeadToken(userDetails);
            return true;
        }
        return false;
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token
     */
    public String generateToken(LoginUserDetails userDetails) {
        String token = UUID.randomUUID().toString();
        userDetails.setToken(token);
        refreshHeadToken(userDetails);
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
//        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(LOGIN_USER_KEY, token);
        return generateToken(claims);
    }

    /**
     * 当原来的token没过期时是可以刷新的
     *
     * @param  loginUserDetails
     */
    public void refreshHeadToken(LoginUserDetails loginUserDetails) {
        loginUserDetails.setExpireTime(System.currentTimeMillis() + expireTime * 1000);
        // 根据uuid将loginUser缓存
//        cacheService.set(CacheConstants.LOGIN_TOKEN_KEY, loginUserDetails.getToken(), loginUserDetails, expireTime);
    }

    /**
     * 判断token在指定时间内是否刚刚刷新过
     * @param token 原token
     * @param time 指定时间（秒）
     */
    private boolean tokenRefreshJustBefore(String token, int time) {
        Claims claims = getClaimsFromToken(token);
        Date created = claims.get(CLAIM_KEY_CREATED, Date.class);
        Date refreshDate = new Date();
        //刷新时间在创建时间的指定时间内
        if(refreshDate.after(created)&&refreshDate.before(DateUtil.offsetSecond(created,time))){
            return true;
        }
        return false;
    }

//    private String getTokenKey(String uuid) {
//        return CacheConstants.LOGIN_TOKEN_KEY + uuid;
//    }
}
