package com.example.rschir_buysell.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN, ROLE_SELLER;

    @Override
    public String getAuthority() {
        return name();
    }
}
