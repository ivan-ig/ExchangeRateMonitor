package com.github.ivanig.exchangeratemonitor.controller;

import com.github.ivanig.exchangeratemonitor.service.GifSelector;
import com.github.ivanig.exchangeratemonitor.service.RateCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ConversionController {

    private final RateCalculator rateCalculator;
    private final GifSelector gifSelector;

    @Autowired
    public ConversionController(RateCalculator rateCalculator, GifSelector gifSelector) {
        this.rateCalculator = rateCalculator;
        this.gifSelector = gifSelector;
    }

    @GetMapping("/{currencyCode}")
    public void redirectToGif(@PathVariable String currencyCode,
                              @RequestParam(value = "basedOn", required = false) String extraBase,
                              HttpServletResponse httpServletResponse) throws IOException {
        int richOrBroke = rateCalculator.calculate(currencyCode, extraBase);
        System.out.println("\n\n" + richOrBroke + "\n\n");
        String gifUrl = gifSelector.selectGif(richOrBroke);
        httpServletResponse.sendRedirect(gifUrl);
    }
}
