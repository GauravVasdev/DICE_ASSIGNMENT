package com.dice.userservice.service;

import com.dice.userservice.http.request.LoginRequest;
import com.dice.userservice.http.response.LoginResponse;

public interface IUserService {
    LoginResponse createAuthenticationToken(LoginRequest loginRequest);

    Boolean validateJwt(String token);
}
