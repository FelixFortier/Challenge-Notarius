package com.example.urlshortener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    UrlRepository mockUrlRepository;

    @InjectMocks
    UrlService urlService;

    @Test
    void getAllUrls() {

        List<Urls> list = new ArrayList<Urls>();
        list.add(new Urls("testlong.com", "testshort.com"));

        when(mockUrlRepository.findAll()).thenReturn(list);

        assertEquals(1, urlService.getAllUrls().size());
        verify(mockUrlRepository, times(1)).findAll();

    }

    @Test
    void getUrlsFromShort() {
        urlService.getUrlsFromShort("test.com");
        urlService.getUrlsFromShort("test2.com");
        urlService.getUrlsFromShort("test2.com");

        verify(mockUrlRepository, times(1)).findUrlsByShortUrl("test.com");
        verify(mockUrlRepository, times(2)).findUrlsByShortUrl("test2.com");
    }

    @Test
    void getUrlsFromLong() {
        urlService.getUrlsFromLong("test.com");
        urlService.getUrlsFromLong("test2.com");
        urlService.getUrlsFromLong("test2.com");

        verify(mockUrlRepository, times(1)).findUrlsByLongUrl("test.com");
        verify(mockUrlRepository, times(2)).findUrlsByLongUrl("test2.com");
    }

    @Test
    void saveUrls() {

        Urls savetest =new Urls("testlong.com", "testshort.com");
        urlService.saveUrls(savetest);
        urlService.saveUrls(savetest);

        verify(mockUrlRepository, times(2)).insert(savetest);




    }
}