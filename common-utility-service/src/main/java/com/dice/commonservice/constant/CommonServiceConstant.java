package com.dice.commonservice.constant;

public class CommonServiceConstant {
    public static final String BASE_URL = "/common";

    public static final String GET_FORECAST_SUMMARY_BY_LOCATION = "/getforecast/summary/location/{country}";
    public static final String GET_HOURLY_FORECAST_SUMMARY_BY_LOCATION = "/gethourlyforecast/summay/location/{country}";
    public static final String GET_TWEETS_BY_USERNAME = "/tweets/getbyusername/{username}";

    public static final String GET_SEARCH_TWEET_USER = "/tweets/getbyquery/{query}";
}
