package com.github.ivanig.exchangeratemonitor.client;

import org.json.simple.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Giphy", url = "${giphyAPIBasePath.url}")
public interface GiphyFeignClient {

    @GetMapping()
    JSONObject findGif(@RequestParam(value = "api_key") String apiKey,
                       @RequestParam(value = "tag") String tag,
                       @RequestParam(value = "rating") String rating);
}
