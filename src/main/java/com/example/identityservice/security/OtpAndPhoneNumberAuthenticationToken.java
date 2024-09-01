package com.example.identityservice.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class OtpAndPhoneNumberAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public OtpAndPhoneNumberAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public OtpAndPhoneNumberAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}
