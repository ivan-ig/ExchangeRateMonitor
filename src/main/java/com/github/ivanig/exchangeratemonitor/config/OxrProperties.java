package com.github.ivanig.exchangeratemonitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oxr")
public class OxrProperties {

    private String appId;
    private String base;
    private String numberOfDaysAgo;

    public OxrProperties() {
    }

    public OxrProperties(String appId, String base, String numberOfDaysAgo) {
        this.appId = appId;
        this.base = base;
        this.numberOfDaysAgo = numberOfDaysAgo;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getNumberOfDaysAgo() {
        return numberOfDaysAgo;
    }

    public void setNumberOfDaysAgo(String numberOfDaysAgo) {
        this.numberOfDaysAgo = numberOfDaysAgo;
    }
}
