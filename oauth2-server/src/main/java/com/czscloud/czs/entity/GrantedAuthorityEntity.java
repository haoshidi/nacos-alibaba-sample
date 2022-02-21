package com.czscloud.czs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GrantedAuthorityEntity implements GrantedAuthority {

    private String authority;
    @Override
    public String getAuthority() {
        return authority;
    }
}
