package com.dice.commonservice.http.controller;

import com.dice.commonservice.constant.CommonServiceConstant;
import com.dice.commonservice.service.ICommonService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.LogManager;

@RestController
@RequestMapping(CommonServiceConstant.BASE_URL)
public class CommonAPIController {

    private Logger logger = LoggerFactory.getLogger(CommonAPIController.class);
    private final ICommonService commonService;

    @Value("${weather.apiKey}")
    private String apiKey;

    @Value("${weather.apiHost}")
    private String apiHost;

    public CommonAPIController(ICommonService commonService) {
        this.commonService = commonService;
    }


    @GetMapping(CommonServiceConstant.GET_FORECAST_SUMMARY_BY_LOCATION)
    public ResponseEntity<String> getRapidAPIForeCastSummaryByLocationName(
            @PathVariable("country") String country){
        logger.info("Controller : Inside getRapidAPIForeCastSummaryByLocationName");
        JSONObject result = commonService.getRapidAPIForeCastSummaryByLocationName(apiKey,apiHost,country);
        return new ResponseEntity<>(result.toString(), HttpStatus.OK);
    }

    @GetMapping(CommonServiceConstant.GET_HOURLY_FORECAST_SUMMARY_BY_LOCATION)
    public ResponseEntity<String> getRapidApiGetHourlyForecastByLocationName(@PathVariable("country") String country){
        logger.info("Controller :Inside getRapidApiGetHourlyForecastByLocationName");
        JSONObject result = commonService.getRapidApiGetHourlyForecastByLocationName("7a850cadd2mshed69e6b6d3449c8p19b74cjsn3eeba7dea2d1","forecast9.p.rapidapi.com",country);
        return new ResponseEntity<>(result.toString(), HttpStatus.OK);
    }

    @GetMapping(CommonServiceConstant.GET_TWEETS_BY_USERNAME)
    public ResponseEntity<List<String>> getTweetsByUsername(@PathVariable("username") String username){
        logger.info("Controller :Inside getTweetsByUsername");
        List<String> listOfTweets = commonService.getTweetsByUsername(username);
        return new ResponseEntity<>(listOfTweets, HttpStatus.OK);
    }

    @GetMapping(CommonServiceConstant.GET_SEARCH_TWEET_USER)
    public ResponseEntity<List<String>> searchTweetUser(@PathVariable("query") String query){
        logger.info("Controller : Inside searchTweetUser");
        List<String> tweetUser = commonService.searchTweetUser(query);
        return new ResponseEntity<>(tweetUser, HttpStatus.OK);
    }


}
