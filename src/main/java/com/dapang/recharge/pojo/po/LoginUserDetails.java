package com.dapang.recharge.pojo.po;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

/**
 * SpringSecurity需要的用户详情
 * Created on 2018/4/26.
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LoginUserDetails implements UserDetails {
    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * token过期时间
     */
    private Long expireTime;
    private UserPO userPO;

    public LoginUserDetails(UserPO userPO) {
        this.userPO = userPO;
    }

    @JSONField(serialize = false)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("TEST"));
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return userPO.getPassword();
    }

    @JSONField(serialize = false)
    @Override
    public String getUsername() {
        return userPO.getUsername();
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return userPO.getIsDeleted() == 0;
    }
}
