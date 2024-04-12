package com.dice.edgeservice.security;


import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;

import com.dice.edgeservice.client.UserAuthenticationAPI;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter
        extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final ApplicationProps applicationProps;

    private UserAuthenticationAPI userAuthenticationAPI;

    public AuthenticationFilter(
            ApplicationProps applicationProps, @Lazy UserAuthenticationAPI userAuthenticationAPI) {
        super(Config.class);
        this.applicationProps = applicationProps;
        this.userAuthenticationAPI = userAuthenticationAPI;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            Predicate<ServerHttpRequest> isApiSecured =
                    r ->
                            applicationProps.getUnfiltered().stream()
                                    .noneMatch(uri -> r.getURI().getPath().contains(uri));
            if (isApiSecured.test(request)) {
                if (!isAuthMissing(request)) {
                    return this.onError(
                            exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
                }
                final String requestTokenHeader = this.getAuthHeader(request);
                if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

                    String jwtToken = requestTokenHeader.substring(7);

                    /** here either userDetails return or error */
                    try {
                        ResponseEntity<Boolean> validateTokenResponse =
                                userAuthenticationAPI.validateJwt(jwtToken);

                        if (validateTokenResponse != null
                                && !validateTokenResponse.getBody()) {
                            throw new ResponseStatusException(
                                    HttpStatus.UNAUTHORIZED, "You are not authorized");
                        }
                    } catch (Exception exception) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, exception.getMessage());
                    }
                } else {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "INVALID TOKEN");
                }
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        byte[] bytes = err.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
        // return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return request.getHeaders().containsKey("Authorization");
    }

    public static class Config {
        // Put the configuration properties
    }
}
