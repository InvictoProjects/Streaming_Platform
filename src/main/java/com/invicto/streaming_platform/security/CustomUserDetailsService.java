package com.invicto.streaming_platform.security;

import com.invicto.streaming_platform.captcha.RequiresCaptcha;
import com.invicto.streaming_platform.persistence.model.User;
import com.invicto.streaming_platform.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceImpl userService;

    @Override
    @RequiresCaptcha
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        try {
            User user = userService.findByLoginOrEmail(input);
            return new org
                    .springframework
                    .security
                    .core
                    .userdetails
                    .User(user.getEmail(), user.getPasswordHash(), new ArrayList<>());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
