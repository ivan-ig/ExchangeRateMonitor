package com.github.ivanig.exchangeratemonitor.client.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class OxrResponse {
    private String disclaimer;
    private String license;
    private Long timestamp;
    private String base;
    private Map<String, BigDecimal> rates;

    public OxrResponse() {
    }

    public OxrResponse(String disclaimer, String license, Long timestamp, String base, Map<String, BigDecimal> rates) {
        this.disclaimer = disclaimer;
        this.license = license;
        this.timestamp = timestamp;
        this.base = base;
        this.rates = rates;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setRates(Map<String, BigDecimal> rates) {
        this.rates = rates;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    @Override
    public String toString() {
        return "\nOxrResponse{" +
                "disclaimer='" + disclaimer + '\'' +
                ", license='" + license + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", base='" + base + '\'' +
                ", rates=" + rates +
                "}\n\n\n";
    }
}
