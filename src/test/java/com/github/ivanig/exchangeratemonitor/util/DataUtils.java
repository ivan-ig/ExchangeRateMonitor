package com.github.ivanig.exchangeratemonitor.util;

import com.github.ivanig.exchangeratemonitor.client.model.OxrResponse;
import org.json.simple.JSONObject;

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

    public static JSONObject getResponseFromGiphy() {
        Map<String, String> data = Map.of("image_original_url", "https://some_path_vars/giphy.gif");
        Map<String, String> meta = Map.of("anything", "anything");
        Map<String, Map<String, String>> response = Map.of("data", data, "meta", meta);
        return new JSONObject(response);
    }
}
