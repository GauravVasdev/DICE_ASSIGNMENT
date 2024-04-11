package com.dice.commonservice.configuration;


import com.dice.commonservice.client.WeatherServiceAPI;
import com.dice.commonservice.client.TwitterServiceAPI;
import com.dice.commonservice.service.ICommonService;
import com.dice.commonservice.service.impl.CommonServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public ICommonService commonService(WeatherServiceAPI weatherServiceAPI, TwitterServiceAPI twitterServiceAPI){
        return new CommonServiceImpl(weatherServiceAPI, twitterServiceAPI);
    }
}
