package com.example.bulletinboard.security;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
@Data
@Getter
@Setter
@RequestScope
public class AuthUser implements UserDetails {
    private AuthUserDto user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(user)
                .map(user -> user.getRole().name())
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .map(Collections::singleton)
                .orElseGet(Collections::emptySet);
    }

    @Override
    public String getPassword() {
        return Optional.ofNullable(user)
                .map(AuthUserDto::getUsername)
                .orElse(null);
    }

    @Override
    public String getUsername() {
        return Optional.ofNullable(user)
                .map(AuthUserDto::getUsername)
                .orElse(null);
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
