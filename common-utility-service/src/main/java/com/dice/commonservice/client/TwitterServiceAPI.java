package com.dice.commonservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "twitter-client", url = "https://api.twitter.com")
public interface TwitterServiceAPI {

    @GetMapping("/1.1/users/search.json")
    List<String> searchTweetUser(@RequestParam("q") String query);

    @GetMapping("/1.1/statuses/user_timeline.json")
    List<String> getUserTweets(@RequestParam("screen_name") String screenName);
}