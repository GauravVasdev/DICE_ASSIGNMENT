package com.dice.commonservice.constant;

public class CommonServiceConstant {
    public static final String BASE_URL = "/common";

    public static final String GET_BASE_URL = "/get/";

    public static final String GET_FORECAST_SUMMARY_BY_LOCATION = GET_BASE_URL+"/forecast/summary/location/{country}";
    public static final String GET_HOURLY_FORECAST_SUMMARY_BY_LOCATION = GET_BASE_URL + "/hourly-forecast/summary/location/{country}";
    public static final String GET_TWEETS_BY_USERNAME = GET_BASE_URL + "/tweets/getbyusername/{username}";

    public static final String GET_SEARCH_TWEET_USER = GET_BASE_URL + "/tweets/getbyquery/{query}";
}
