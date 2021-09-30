package com.github.ivanig.exchangeratemonitor.client;

import com.github.ivanig.exchangeratemonitor.client.model.OxrResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "OpenExchangeRates",url = "${oxrAPIBasePath.url}")
public interface OxrFeignClient {

    @GetMapping("/latest.json")
    OxrResponse getCurrentRate(@RequestParam(value = "app_id") String appId,
                               @RequestParam(value = "base") String base);

    @GetMapping("/historical/{date}.json")
    OxrResponse getCustomDateRate(@PathVariable(value = "date") String date,
                                  @RequestParam(value = "app_id") String appId,
                                  @RequestParam(value = "base") String base);
}
