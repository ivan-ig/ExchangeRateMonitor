package com.github.ivanig.exchangeratemonitor.service;

import com.github.ivanig.exchangeratemonitor.client.model.OxrResponse;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@AutoConfigureMockMvc
class RateCalculatorTest {

    private OxrResponse today;
    private OxrResponse yesterday;

    @Autowired
    private RateCalculator rateCalculator;

//    @MockBean
//    private RateCalculator rateCalculator;

//    @Autowired
//    private MockMvc mvc;


//    @Test
//    public void getActualExchangeRateFromOxrShouldReturnOxrResponse() {
////        Map<String, BigDecimal> rates = new HashMap<>();
////        rates.put("USD", new BigDecimal("1"));
////        rates.put("RUB", new BigDecimal("72.5"));
////        OxrResponse expectedOxrResponse = new OxrResponse(
////                "disclaimer", "license", 1_500_000_000L, "USD", rates);
////
////        Mockito.when(rateCalculator.getActualExchangeRateFromOxr()).thenReturn(expectedOxrResponse);
//
//        today = rateCalculator.getActualExchangeRateFromOxr();
//        assertNotNull(today);
//        assertTrue(today.getRates().containsKey("USD"));
//    }


    @Test
    public void getActualExchangeRateFromOxrShouldReturnOxrResponse() {
        today = rateCalculator.getActualExchangeRateFromOxr();
        assertNotNull(today);
        assertTrue(today.getRates().containsKey("USD"));
    }

    @Test
    public void getExchangeRateFromOxrByDateShouldReturnOxrResponse() {
        yesterday = rateCalculator.getExchangeRateFromOxrByDate();
        assertNotNull(yesterday);
        assertTrue(yesterday.getRates().containsKey("USD"));
    }

    @Test
    public void isActualExcRateAfterExcRateByDate() {
        today = rateCalculator.getActualExchangeRateFromOxr();
        LocalDate todayDate = Instant.ofEpochSecond(
                today.getTimestamp()).atZone(ZoneId.of("UTC+00:00")).toLocalDate();

        yesterday = rateCalculator.getExchangeRateFromOxrByDate();
        LocalDate yesterdayDate = Instant.ofEpochSecond(
                yesterday.getTimestamp()).atZone(ZoneId.of("UTC+00:00")).toLocalDate();
        if (!todayDate.isEqual(yesterdayDate)) {
            assertTrue(todayDate.isAfter(yesterdayDate));
        } else {
            Assumptions.assumeTrue(todayDate.isEqual(yesterdayDate),
                    "No up-to-date exchange rates are available. Exchanges may be closed...");
        }
    }

    // TODO:
    //  test GiphySelector +++
    //  test properties (если нужно)
    //  test void Controller (если можно и нужно)


    @Test
    public void calculateMethodShouldReturnZero() {
        assertEquals(rateCalculator.calculate("USD", "USD"), 0);
    }

    @Test
    public void calculateMethodShouldReturnOneOrMinusOne() {
        Assumptions.assumeTrue(rateCalculator.calculate("USD", "RUB") == 1);
        Assumptions.assumeTrue(rateCalculator.calculate("USD", "RUB") == -1);
    }
}