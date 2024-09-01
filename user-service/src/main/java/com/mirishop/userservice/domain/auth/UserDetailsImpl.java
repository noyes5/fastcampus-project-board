package com.mirishop.userservice.domain.auth;

import com.mirishop.userservice.domain.user.UserEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Builder
public class UserDetailsImpl implements UserDetails {

    private final String email;
    private final String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl from(UserEntity userEntity) {
        return UserDetailsImpl.builder()
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole())))
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
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
