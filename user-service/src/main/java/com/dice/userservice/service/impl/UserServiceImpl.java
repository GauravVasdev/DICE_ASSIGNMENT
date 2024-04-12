package com.dice.userservice.service.impl;

import com.dice.userservice.http.request.LoginRequest;
import com.dice.userservice.http.response.LoginResponse;
import com.dice.userservice.model.Token;
import com.dice.userservice.model.User;
import com.dice.userservice.model.custom.AuthenticationInfo;
import com.dice.userservice.port.persistence.ITokenRepository;
import com.dice.userservice.port.persistence.IUserRepository;
import com.dice.userservice.service.IUserService;
import com.dice.userservice.util.JwtTokenUtil;
import com.dice.userservice.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceImpl implements IUserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final JwtTokenUtil jwtTokenUtil;

    private final IUserRepository userRepository;

    private final ITokenRepository tokenRepository;

    public UserServiceImpl(JwtTokenUtil jwtTokenUtil, IUserRepository userRepository, ITokenRepository tokenRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    private AuthenticationInfo authenticate(String username, String password){
        var user = userRepository.findByUsernameAndPassword(username, password);
        if (user == null || user.isEmpty()) {
            throw new RuntimeException("User Not Found");
        }
        String token = jwtTokenUtil.generateToken(user.get());
        AuthenticationInfo authenticationInfo = new AuthenticationInfo();
        authenticationInfo.setUser(user.get());
        authenticationInfo.setToken(token);
        return authenticationInfo;
    }
    @Override
    public LoginResponse createAuthenticationToken(LoginRequest loginRequest) {
        logger.info("UserServiceImpl : Inside createAuthenticationToken()");
        AuthenticationInfo authenticationInfo = authenticate(loginRequest.userName(), loginRequest.password());
        String token = authenticationInfo.getToken();
        if (token != null) {
            User user = authenticationInfo.getUser();
            Token tokenModel = new Token();
            tokenModel.setToken(token);
            tokenModel.setUser(user);
            tokenModel.setTokenUuid(UUIDUtil.randomUUID());
           tokenRepository.save(tokenModel);
        }
        LoginResponse loginResponse = new LoginResponse(token);
        return loginResponse;
    }

    @Override
    public Boolean validateJwt(String token) {
        String userName = jwtTokenUtil.getUserNameFromToken(token);
        logger.info("UserServiceImpl : Inside validateJWT()");
        if (userName.equals("Invalid Token")) {
            throw new RuntimeException("Invalid Token");
        }
        Token tokenModel =   tokenRepository.getByToken(token);
        if (tokenModel!=null) {
            if (jwtTokenUtil.isTokenExpired(token)) {
                throw new RuntimeException("Token expired");
            }
        } else {
            throw new RuntimeException("Token Not Present");
        }
        return true;
    }
}
