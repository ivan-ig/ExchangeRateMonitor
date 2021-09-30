package com.github.ivanig.exchangeratemonitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "giphy")
public class GiphyProperties {

    private String appId;
    private String tagRich;
    private String tagBroke;
    private String rating;
    private String noChangeGifUrl;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTagRich() {
        return tagRich;
    }

    public void setTagRich(String tagRich) {
        this.tagRich = tagRich;
    }

    public String getTagBroke() {
        return tagBroke;
    }

    public void setTagBroke(String tagBroke) {
        this.tagBroke = tagBroke;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getNoChangeGifUrl() {
        return noChangeGifUrl;
    }

    public void setNoChangeGifUrl(String noChangeGifUrl) {
        this.noChangeGifUrl = noChangeGifUrl;
    }
}
