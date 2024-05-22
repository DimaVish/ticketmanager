package com.smartticket.ticketmanager.security;

import com.smartticket.ticketmanager.repository.UserRepository;
import com.smartticket.ticketmanager.repository.entities.User;
import com.smartticket.ticketmanager.security.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("Entering in loadUserByUsername Method...");
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("could not found user..!!"));
        log.info("User Authenticated Successfully..!!!");
        return new CustomUserDetails(user);
    }
}