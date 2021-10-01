package com.github.ivanig.exchangeratemonitor.service;

import com.github.ivanig.exchangeratemonitor.client.GiphyFeignClient;
import com.github.ivanig.exchangeratemonitor.config.GiphyProperties;
import com.github.ivanig.exchangeratemonitor.util.DataUtils;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GifSelectorTest {

    @Autowired
    private GifSelector gifSelector;

    @Autowired
    private GiphyProperties giphyProperties;

    @MockBean
    private GiphyFeignClient giphyFeignClient;

    @Test
    public void selectGifShouldReturnStringUrl() {
        JSONObject giphyResponse = DataUtils.getResponseFromGiphy();

        Mockito.when(giphyFeignClient.findGif(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(giphyResponse);

        final String gifUrl = gifSelector.selectGif(1);
        assertTrue(gifUrl.startsWith("https://") && gifUrl.endsWith("giphy.gif"));
    }

    @Test
    public void selectGifShouldReturnSpecifiedStringUrl() {
        JSONObject giphyResponse = DataUtils.getResponseFromGiphy();

        Mockito.when(giphyFeignClient.findGif(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(giphyResponse);

        final String gifUrl = gifSelector.selectGif(0);
        assertEquals(gifUrl, giphyProperties.getNoChangeGifUrl());
    }
}