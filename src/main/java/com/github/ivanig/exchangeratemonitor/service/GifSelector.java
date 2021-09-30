package com.github.ivanig.exchangeratemonitor.service;

import com.github.ivanig.exchangeratemonitor.client.GiphyFeignClient;
import com.github.ivanig.exchangeratemonitor.config.GiphyProperties;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class GifSelector {

    private final GiphyFeignClient giphyFeignClient;
    private final GiphyProperties properties;

    public GifSelector(GiphyFeignClient giphyFeignClient, GiphyProperties properties) {
        this.giphyFeignClient = giphyFeignClient;
        this.properties = properties;
    }

    public String selectGif(int richOrBroke) {
        JSONObject giphyResponse;
        String originalGifUrl = properties.getNoChangeGifUrl();
        if (richOrBroke == -1) {
            giphyResponse = giphyFeignClient.findGif(properties.getAppId(), properties.getTagBroke(),
                    properties.getRating());
            originalGifUrl = searchGifUrl(giphyResponse);
        }
        if (richOrBroke == 1) {
            giphyResponse = giphyFeignClient.findGif(properties.getAppId(), properties.getTagRich(),
                    properties.getRating());
            originalGifUrl = searchGifUrl(giphyResponse);
        }
        return originalGifUrl;
    }

    private String searchGifUrl(JSONObject giphyResponse) {
        return JsonPath.read(giphyResponse, "$.data.image_original_url");
    }
}
