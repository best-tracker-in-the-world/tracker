package ru.rogzy.tracker_backend.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private final Long userId;
    private final String email;

    @Override
    public String getUsername() {
        return email;
    }

    // Остальные методы UserDetails:
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
    @Override public String getPassword() { return null; } // или "" если не используется
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return List.of(); }
}
