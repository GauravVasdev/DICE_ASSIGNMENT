package com.dice.commonservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "weather-service", url = "https://forecast9.p.rapidapi.com/rapidapi/forecast")
public interface WeatherServiceAPI {

    @GetMapping("/{country}/summary/")
    ResponseEntity<String> getRapidAPIForeCastSummaryByLocationName(@RequestHeader("X-RapidAPI-Key") String apiKey, @RequestHeader("X-RapidAPI-Host") String apiHost, @PathVariable("country") String country);

    @GetMapping("/{country}/hourly/")
    ResponseEntity<String> getRapidApiGetHourlyForecastByLocationName(@RequestHeader("X-RapidAPI-Key") String apiKey, @RequestHeader("X-RapidAPI-Host") String apiHost, @PathVariable("country") String country);
}
