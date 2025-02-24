package vn.hoidanit.jobhunter.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;

@Component("userDetailsService")
public class CustomUserDetailService implements UserDetailsService {

    private UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        User us = this.userService.findUserByEmail(username);

        return new org.springframework.security.core.userdetails.User(
                us.getEmail(),
                us.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

    }

}
