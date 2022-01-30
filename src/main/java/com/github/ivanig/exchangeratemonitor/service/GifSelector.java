package com.github.ivanig.exchangeratemonitor.service;

import com.github.ivanig.exchangeratemonitor.client.GiphyFeignClient;
import com.github.ivanig.exchangeratemonitor.config.GiphyProperties;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
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
            giphyResponse = getGiphyResponse(properties.getTagBroke());
            originalGifUrl = lookForGifUrl(giphyResponse);
        }
        if (richOrBroke == 1) {
            giphyResponse = getGiphyResponse(properties.getTagRich());
            originalGifUrl = lookForGifUrl(giphyResponse);
        }
        return originalGifUrl;
    }

    private JSONObject getGiphyResponse(String tag) {
        return giphyFeignClient.findGif(properties.getAppId(), tag, properties.getRating());
    }

    private String lookForGifUrl(JSONObject giphyResponse) {
        return JsonPath.read(giphyResponse, "$.data.url");
    }
}
