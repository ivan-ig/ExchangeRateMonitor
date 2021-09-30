package com.github.ivanig.exchangeratemonitor;

import com.github.ivanig.exchangeratemonitor.config.GiphyProperties;
import com.github.ivanig.exchangeratemonitor.config.OxrProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.github.ivanig.exchangeratemonitor.client")
@EnableConfigurationProperties({OxrProperties.class, GiphyProperties.class})
public class ExchangeRateMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeRateMonitorApplication.class, args);
    }
}
