package com.CN.Gym.service;

import com.CN.Gym.dto.JwtRequest;
import com.CN.Gym.dto.JwtResponse;
import com.CN.Gym.jwt.JwtAuthenticationHelper;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    AuthenticationManager manager;

    @Autowired
    JwtAuthenticationHelper jwtHelper;

    @Autowired
    UserDetailsService userDetailsService;
    
    Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    private Counter loginCounter = null;
    
    public AuthService(CompositeMeterRegistry meterRegistry) {
    	loginCounter = meterRegistry.counter("count.of.login");
    }

    public JwtResponse login(JwtRequest jwtRequest) {

        //authenticate with Authentication manager
        this.doAuthenticate(jwtRequest.getUsername(),jwtRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtHelper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder().jwtToken(token).build();
        
        loginCounter.increment();
        return response;
    }

    private void doAuthenticate(String username, String password) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            manager.authenticate(authenticationToken);

        }catch (BadCredentialsException e) {
        	logger.error("Invalid Credentials");
            throw new BadCredentialsException("Invalid Username or Password");
        }
    }

}