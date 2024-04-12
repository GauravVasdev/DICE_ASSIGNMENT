package com.dice.userservice.config;

import com.dice.userservice.port.persistence.ITokenRepository;
import com.dice.userservice.port.persistence.IUserRepository;
import com.dice.userservice.service.IUserService;
import com.dice.userservice.service.impl.UserServiceImpl;
import com.dice.userservice.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public JwtTokenUtil jwtTokenUtil(){
        return new JwtTokenUtil();
    }
    @Bean
    public IUserService userService(JwtTokenUtil jwtTokenUtil, IUserRepository userRepository, ITokenRepository tokenRepository){
        return new UserServiceImpl(jwtTokenUtil, userRepository, tokenRepository);
    }
}
