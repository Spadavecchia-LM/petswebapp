package com.educacionIT.CLASE1.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if("user".equals(username)){
            return User.withUsername("user")
                    .password("{noop}pass")
                    .roles("USER")
                    .build();
        }
        else {
            throw new UsernameNotFoundException("no se encontro el usuario");
        }
    }
}
