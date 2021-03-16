package com.invicto.streamingPlatform.security;

import com.invicto.streamingPlatform.persistence.model.User;
import com.invicto.streamingPlatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String input = authentication.getName();
        String password = authentication.getCredentials().toString();
        Optional<User> optionalUser = userService.findByLoginOrEmail(input);
        if (optionalUser.isEmpty()) {
            throw new BadCredentialsException("Unknown user "+input);
        }
        if (!password.equals(optionalUser.get().getPassword())) {
            throw new BadCredentialsException("Bad password");
        }
        UserDetails principal = org.springframework.security.core.userdetails.User.builder()
                .username(optionalUser.get().getLogin())
                .password(optionalUser.get().getPassword())
                .roles("USER")
                .build();
        return new UsernamePasswordAuthenticationToken(principal, password, principal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
