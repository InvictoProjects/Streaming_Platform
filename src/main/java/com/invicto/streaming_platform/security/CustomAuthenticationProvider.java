package com.invicto.streaming_platform.security;

import com.invicto.streaming_platform.persistence.model.User;
import com.invicto.streaming_platform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String input = authentication.getName();
        String passwordHash = authentication.getCredentials().toString();
        User userEntity = userService.findByLoginOrEmail(input);
        if (!passwordHash.equals(userEntity.getPasswordHash())) {
            throw new BadCredentialsException("Bad passwordHash");
        }
        UserDetails principal = org.springframework.security.core.userdetails.User.builder()
                .username(userEntity.getLogin())
                .password(userEntity.getPasswordHash())
                .roles("USER")
                .build();
        return new UsernamePasswordAuthenticationToken(principal, passwordHash, principal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
