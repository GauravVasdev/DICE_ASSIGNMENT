package com.dice.userservice.http.controller;

import com.dice.userservice.constant.UserServiceConstants;
import com.dice.userservice.http.request.LoginRequest;
import com.dice.userservice.http.response.LoginResponse;
import com.dice.userservice.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserServiceConstants.BASE_URL)
public class UserServiceAPIController {

    private final IUserService userService;

    private Logger logger = LoggerFactory.getLogger(UserServiceAPIController.class);

    public UserServiceAPIController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(UserServiceConstants.LOGIN_URL)
    public ResponseEntity<LoginResponse> createAuthenticationToken(
            @RequestBody LoginRequest loginRequest) {
        logger.info("Controller : Inside createAuthenticationToken()");
        LoginResponse response = userService.createAuthenticationToken(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = UserServiceConstants.VALIDATE_TOKEN_URL)
    public ResponseEntity<Boolean> validateJwt(@RequestParam("token") String token) {
        logger.info("Controller : Inside validateJwt()");
        Boolean status = userService.validateJwt(token);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
