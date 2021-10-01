package com.github.ivanig.exchangeratemonitor.util;

import com.github.ivanig.exchangeratemonitor.client.model.OxrResponse;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public class DataUtils {

    public static OxrResponse getOxrResponseByInstant(Instant instant, long rubValue) {
        return new OxrResponse(
                "disclaimer",
                "someLicense",
                instant.getEpochSecond(),
                "USD",
                Map.of("USD", BigDecimal.ONE,
                        "RUB", BigDecimal.valueOf(rubValue))
        );
    }
}
