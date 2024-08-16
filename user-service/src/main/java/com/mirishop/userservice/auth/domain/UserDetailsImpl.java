package com.mirishop.userservice.auth.domain;

import com.mirishop.userservice.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Getter
@Builder
public class UserDetailsImpl implements UserDetails {

    private final String email;
    private final String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl from(User user) {
        return UserDetailsImpl.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(Set.of())   // 추후 role로 수정
                .build();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + );
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
