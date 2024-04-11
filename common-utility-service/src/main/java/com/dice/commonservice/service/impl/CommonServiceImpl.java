package com.dice.commonservice.service.impl;

import com.dice.commonservice.client.WeatherServiceAPI;
import com.dice.commonservice.client.TwitterServiceAPI;
import com.dice.commonservice.service.ICommonService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class CommonServiceImpl implements ICommonService {

    private Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);
    public WeatherServiceAPI weatherServiceAPI;

    public TwitterServiceAPI twitterServiceAPI;

    public CommonServiceImpl(WeatherServiceAPI weatherServiceAPI, TwitterServiceAPI twitterServiceAPI) {
        this.weatherServiceAPI = weatherServiceAPI;
        this.twitterServiceAPI = twitterServiceAPI;
    }

    @Override
    public JSONObject getRapidAPIForeCastSummaryByLocationName(String apiKey, String apiHost, String country) {
        logger.info("Service : Inside getRapidAPIForeCastSummaryByLocationName");
        ResponseEntity<String> result = weatherServiceAPI.getRapidAPIForeCastSummaryByLocationName(apiKey, apiHost, country);
        JSONObject jsonObject = new JSONObject(result.getBody());
        return jsonObject;
    }

    @Override
    public JSONObject getRapidApiGetHourlyForecastByLocationName(String apiKey, String apiHost, String country) {
        logger.info("Service : Inside getRapidApiGetHourlyForecastByLocationName");
        ResponseEntity<String> result = weatherServiceAPI.getRapidApiGetHourlyForecastByLocationName(apiKey, apiHost, country);
        JSONObject jsonObject = new JSONObject(result.getBody());
        return jsonObject;
    }

    @Override
    public List<String> getTweetsByUsername(String username) {
        logger.info("Service : Inside getTweetsByUsername");
        return twitterServiceAPI.getUserTweets(username);
    }

    @Override
    public List<String> searchTweetUser(String query) {
        logger.info("Service : Inside searchTweetUser");
        return twitterServiceAPI.searchTweetUser(query);
    }
}
