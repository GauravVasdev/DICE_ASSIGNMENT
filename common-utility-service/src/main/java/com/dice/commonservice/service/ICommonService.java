package com.dice.commonservice.service;

import org.json.JSONObject;

import java.util.List;

public interface ICommonService {

    JSONObject getRapidAPIForeCastSummaryByLocationName(String apiKey, String apiHost, String country);

    JSONObject getRapidApiGetHourlyForecastByLocationName(String apiKey, String apiHost, String country);

    List<String> getTweetsByUsername(String username);

    List<String> searchTweetUser(String query);
}
