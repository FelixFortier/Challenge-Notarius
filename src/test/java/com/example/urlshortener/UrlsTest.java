package com.example.urlshortener;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlsTest {

    @Test
    void gettersAndSetter() {
        Urls urls = new Urls("longUrl", "shortUrl");
        assertEquals("longUrl", urls.getLongUrl());
        assertEquals("shortUrl", urls.getShortUrl());
        urls.setShortUrl("shorterUrl");
        urls.setLongUrl("longerUrl");
        assertEquals("longerUrl", urls.getLongUrl());
        assertEquals("shorterUrl", urls.getShortUrl());
    }
}