package com.selflearn.tree.securityConfiguration;

import com.selflearn.tree.model.UserModel;
import com.selflearn.tree.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: find user from database table accounts by username
        //  and return user accordingly (Done)
        UserModel userModel = userRepository.findByUserName(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_"+userModel.getRole());
        return new User(userModel.getUsername(), userModel.getPassword(), Collections.singleton(simpleGrantedAuthority));
    }
}
