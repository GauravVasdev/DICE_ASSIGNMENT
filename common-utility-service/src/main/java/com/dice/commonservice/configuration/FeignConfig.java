package com.dice.commonservice.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Autowired
    private OAuth1Configuration oAuth1Configuration;

    @Bean
    public OAuth1Interceptor  requestInterceptor() {
        return new OAuth1Interceptor(
                oAuth1Configuration.getConsumerKey(),
                oAuth1Configuration.getConsumerSecret(),
                oAuth1Configuration.getAccessToken(),
                oAuth1Configuration.getAccessTokenSecret());
        };
}
