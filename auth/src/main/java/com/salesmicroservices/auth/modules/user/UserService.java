package com.salesmicroservices.auth.modules.user;

import com.salesmicroservices.auth.config.exception.NotFoundException;
import com.salesmicroservices.auth.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username)
            .orElseThrow(() -> new NotFoundException("User ".concat(username).concat(" not found.")));
    }
}
