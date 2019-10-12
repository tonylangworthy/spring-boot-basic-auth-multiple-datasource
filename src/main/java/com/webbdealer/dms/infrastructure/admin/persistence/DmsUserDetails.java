package com.webbdealer.dms.infrastructure.admin.persistence;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = false)
public class DmsUserDetails extends User {

    public DmsUserDetails(String username,
                          String password,
                          Boolean enabled,
                          Boolean accountNonExpired,
                          Boolean credentialsNonExpired,
                          Boolean accountNonLocked,
                          Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    private Long accountId;

    @Getter(AccessLevel.NONE)
    private String tenantId;

    private String firstname;

    private String lastname;

    public String getTenantId() {
        return "account_" + accountId;
    }
}
