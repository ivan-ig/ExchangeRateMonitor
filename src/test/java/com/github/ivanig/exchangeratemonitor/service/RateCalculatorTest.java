package com.github.ivanig.exchangeratemonitor.service;

import com.github.ivanig.exchangeratemonitor.client.OxrFeignClient;
import com.github.ivanig.exchangeratemonitor.client.model.OxrResponse;
import com.github.ivanig.exchangeratemonitor.config.OxrProperties;
import com.github.ivanig.exchangeratemonitor.util.DataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@SpringBootTest
class RateCalculatorTest {

    @Autowired
    private RateCalculator rateCalculator;

    @Autowired
    private OxrProperties properties;

    @MockBean
    private OxrFeignClient oxrFeignClient;

    @Test
    public void calculateShouldReturnMinusOne() {
        OxrResponse todayOxrResponse = DataUtils.getOxrResponseByInstant(Instant.now(), 29L);
        OxrResponse customDateOxrResponse = DataUtils.getOxrResponseByInstant(
                Instant.now().minus(Long.parseLong(properties.getNumberOfDaysAgo()), ChronoUnit.DAYS), 33L);

        Mockito.when(oxrFeignClient.getCurrentRate(Mockito.any(), Mockito.any()))
                .thenReturn(todayOxrResponse);
        Mockito.when(oxrFeignClient.getCustomDateRate(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(customDateOxrResponse);

        final int result = rateCalculator.calculate("RUB", null);

        Assertions.assertEquals(result, -1);
    }

    @Test
    public void calculateShouldReturnOne() {
        OxrResponse todayOxrResponse = DataUtils.getOxrResponseByInstant(Instant.now(), 88L);
        OxrResponse customDateOxrResponse = DataUtils.getOxrResponseByInstant(
                Instant.now().minus(Long.parseLong(properties.getNumberOfDaysAgo()), ChronoUnit.DAYS), 49L);

        Mockito.when(oxrFeignClient.getCurrentRate(Mockito.any(), Mockito.any()))
                .thenReturn(todayOxrResponse);
        Mockito.when(oxrFeignClient.getCustomDateRate(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(customDateOxrResponse);

        final int result = rateCalculator.calculate("RUB", "something");

        Assertions.assertEquals(result, 1);
    }

    @Test
    public void calculateShouldReturnZero() {
        OxrResponse todayOxrResponse = DataUtils.getOxrResponseByInstant(Instant.now(), 75L);
        OxrResponse customDateOxrResponse = DataUtils.getOxrResponseByInstant(
                Instant.now().minus(Long.parseLong(properties.getNumberOfDaysAgo()), ChronoUnit.DAYS), 75L);

        Mockito.when(oxrFeignClient.getCurrentRate(Mockito.any(), Mockito.any()))
                .thenReturn(todayOxrResponse);
        Mockito.when(oxrFeignClient.getCustomDateRate(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(customDateOxrResponse);

        final int result = rateCalculator.calculate("RUB", null);

        Assertions.assertEquals(result, 0);
    }

    @Test
    public void rateCalculationBasedOnSameCurrencyShouldReturnZero() {
        OxrResponse todayOxrResponse = DataUtils.getOxrResponseByInstant(Instant.now(), 88L);
        OxrResponse customDateOxrResponse = DataUtils.getOxrResponseByInstant(
                Instant.now().minus(Long.parseLong(properties.getNumberOfDaysAgo()), ChronoUnit.DAYS), 49L);

        Mockito.when(oxrFeignClient.getCurrentRate(Mockito.any(), Mockito.any()))
                .thenReturn(todayOxrResponse);
        Mockito.when(oxrFeignClient.getCustomDateRate(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(customDateOxrResponse);

        final int result = rateCalculator.calculate("RUB", "RUB");
        final int resultInUsd = rateCalculator.calculate("USD", null);
        final int resultInUsdBasedOnWhatever = rateCalculator.calculate("USD", "whatever");

        Assertions.assertEquals(result, 0);
        Assertions.assertEquals(resultInUsd, 0);
        Assertions.assertEquals(resultInUsdBasedOnWhatever, 0);
    }
}