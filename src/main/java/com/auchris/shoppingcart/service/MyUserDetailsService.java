package com.auchris.shoppingcart.service;

import com.auchris.shoppingcart.model.User;
import com.auchris.shoppingcart.repository.UserRepository;
import com.auchris.shoppingcart.security.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
Custom UserDetailsService to pull principle (User) from database
during authentication
 */

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new MyUserPrincipal(user);
    }
}
