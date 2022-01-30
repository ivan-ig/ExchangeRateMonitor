package com.github.ivanig.exchangeratemonitor.service;

import com.github.ivanig.exchangeratemonitor.client.OxrFeignClient;
import com.github.ivanig.exchangeratemonitor.client.model.OxrResponse;
import com.github.ivanig.exchangeratemonitor.config.OxrProperties;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class RateCalculator {

    private final OxrFeignClient oxrFeignClient;
    private final OxrProperties properties;

    public RateCalculator(OxrFeignClient oxrFeignClient, OxrProperties properties) {
        this.oxrFeignClient = oxrFeignClient;
        this.properties = properties;
    }

    public int calculate(String currencyCode, String extraBase) {
        OxrResponse exchangeRate = getActualExchangeRateFromOxr();
        OxrResponse rateByDate = getExchangeRateFromOxrByDate(getCustomDate());

        BigDecimal todayRubleValue = exchangeRate.getRates().get("RUB");
        BigDecimal customDateRubleValue = rateByDate.getRates().get("RUB");
        BigDecimal todayRateValue = exchangeRate.getRates().get(currencyCode);
        BigDecimal customDateRateValue = rateByDate.getRates().get(currencyCode);

        if (extraBase != null && extraBase.equals("RUB")) {
            todayRateValue = todayRateValue.divide(todayRubleValue, 16, RoundingMode.HALF_UP);
            customDateRateValue = customDateRateValue.divide(customDateRubleValue, 16, RoundingMode.HALF_UP);
        }

        System.out.println("todayRateValue" + todayRateValue);
        System.out.println("customDateRateValue" + customDateRateValue);

        return todayRateValue.compareTo(customDateRateValue);
    }

    private OxrResponse getActualExchangeRateFromOxr() {
        return oxrFeignClient.getCurrentRate(properties.getAppId(), properties.getBase());
    }

    private OxrResponse getExchangeRateFromOxrByDate(String date) {
        return oxrFeignClient.getCustomDateRate(date, properties.getAppId(), properties.getBase());
    }

    private String getCustomDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        long numberOfDaysAgo = Long.parseLong(properties.getNumberOfDaysAgo());
        return LocalDate.now().minusDays(numberOfDaysAgo).format(formatter);
    }
}
