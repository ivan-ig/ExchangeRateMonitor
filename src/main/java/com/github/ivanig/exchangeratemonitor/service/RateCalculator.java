package com.github.ivanig.exchangeratemonitor.service;

import com.github.ivanig.exchangeratemonitor.client.OxrFeignClient;
import com.github.ivanig.exchangeratemonitor.client.model.OxrResponse;
import com.github.ivanig.exchangeratemonitor.config.OxrProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class RateCalculator {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OxrFeignClient oxrFeignClient;
    private final OxrProperties properties;
    private OxrResponse todayExchangeRate;
    private OxrResponse customDateExchangeRate;

    public RateCalculator(OxrFeignClient oxrFeignClient, OxrProperties properties,
                          OxrResponse todayRates, OxrResponse customDateExchangeRate) {
        this.oxrFeignClient = oxrFeignClient;
        this.properties = properties;
        this.todayExchangeRate = todayRates;
        this.customDateExchangeRate = customDateExchangeRate;
    }

    public int calculate(String currencyCode, String extraBase) {
        setTodayExchangeRate(getActualExchangeRateFromOxr());
        setCustomDateExchangeRate(getExchangeRateFromOxrByDate());
        return analyzeTheExchangeRate(currencyCode, extraBase);
    }

    public OxrResponse getActualExchangeRateFromOxr() {
//////////////////
        System.out.println("Get oxrRequest (relevant) has been returned");
        return oxrFeignClient.getCurrentRate(properties.getAppId(), properties.getBase());
    }

    public OxrResponse getExchangeRateFromOxrByDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        long numberOfDaysAgo = Long.parseLong(properties.getNumberOfDaysAgo());
        if (numberOfDaysAgo == 0L) {
            numberOfDaysAgo = 1L;
        }
        String date = LocalDate.now().minusDays(numberOfDaysAgo).format(formatter);
///////////////////
        System.out.println("Get oxrRequest (custom date) has been returned");
        return oxrFeignClient.getCustomDateRate(date, properties.getAppId(), properties.getBase());
    }

    private int analyzeTheExchangeRate(String currencyCode, String extraBase) {
        BigDecimal todayRubleValue = todayExchangeRate.getRates().get("RUB");
        BigDecimal yesterdayRubleValue = customDateExchangeRate.getRates().get("RUB");
        BigDecimal todayRateValue = todayExchangeRate.getRates().get(currencyCode);
        BigDecimal yesterdayRateValue = customDateExchangeRate.getRates().get(currencyCode);

        if (extraBase != null && extraBase.equals("RUB")) {
            todayRateValue = todayRateValue.divide(todayRubleValue, 6, RoundingMode.FLOOR);
            yesterdayRateValue = yesterdayRateValue.divide(yesterdayRubleValue, 6, RoundingMode.FLOOR);
        }
///////////////////////

        LocalDate today = Instant.ofEpochSecond(
                todayExchangeRate.getTimestamp()).atZone(ZoneId.of("UTC+00:00")).toLocalDate();
        LocalDate yesterday = Instant.ofEpochSecond(
                customDateExchangeRate.getTimestamp()).atZone(ZoneId.of("UTC+00:00")).toLocalDate();

        logger.info(" today date {}", today);
        logger.info(" yesterday date {}", yesterday);

        logger.info(" today {}", todayExchangeRate);
        logger.info(" today {}", todayRateValue + currencyCode);
        logger.info(" yesterday {}", customDateExchangeRate);
        logger.info(" yesterday {}", yesterdayRateValue + currencyCode);

        return todayRateValue.compareTo(yesterdayRateValue);
    }

    private void setTodayExchangeRate(OxrResponse todayExchangeRate) {
        this.todayExchangeRate = todayExchangeRate;
    }

    private void setCustomDateExchangeRate(OxrResponse customDateExchangeRate) {
        this.customDateExchangeRate = customDateExchangeRate;
    }
}
