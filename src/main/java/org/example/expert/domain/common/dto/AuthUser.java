package org.example.expert.domain.common.dto;

import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class AuthUser implements UserDetails {

    private final Long id;
    private final String email;
    private final UserRole userRole;
    private final String username;

    public AuthUser(Long id, String email, String username, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name()));
    }

    @Override
    public String getPassword() {
        return "";
    }
}
