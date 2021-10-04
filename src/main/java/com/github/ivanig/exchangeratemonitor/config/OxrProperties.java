package com.github.ivanig.exchangeratemonitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Validated
@ConfigurationProperties(prefix = "oxr")
public class OxrProperties {

    @NotBlank
    private String appId;

    @Size(min = 3, max = 3)
    @Pattern(regexp = "[A-Z]+")
    private String base;

    @NotBlank
    @Min(1)
    @Digits(integer = 3, fraction = 0)
    private String numberOfDaysAgo;

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
