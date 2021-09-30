package com.github.ivanig.exchangeratemonitor.service;

import com.github.ivanig.exchangeratemonitor.config.GiphyProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GifSelectorTest {

    @Autowired
    private GifSelector gifSelector;

    @Autowired
    private GiphyProperties giphyProperties;

    private static final String urlBeginning = "https://";

    @Test
    public void selectGifShouldReturnStringUrl() {
        assertTrue(gifSelector.selectGif(0).startsWith(urlBeginning));
        assertEquals(giphyProperties.getNoChangeGifUrl(), gifSelector.selectGif(0));
    }

    @Test
    public void selectGifShouldReturnStringUrlWhenArgumentEqualsMinusOne() {
        assertTrue(gifSelector.selectGif(-1).startsWith(urlBeginning));
    }

    @Test
    public void selectGifShouldReturnStringUrlWhenArgumentEqualsOne() {
        assertTrue(gifSelector.selectGif(1).startsWith(urlBeginning));
    }
}