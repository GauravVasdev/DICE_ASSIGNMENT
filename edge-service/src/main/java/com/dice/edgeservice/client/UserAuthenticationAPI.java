package com.dice.edgeservice.client;

import com.dice.edgeservice.constant.EdgeServiceConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${service.user.name}", url = "${service.user.url}")
public interface UserAuthenticationAPI {

    @PostMapping(EdgeServiceConstants.VALIDATE_TOKEN_URL)
    public ResponseEntity<Boolean> validateJwt(@RequestParam("token") String token);
}
