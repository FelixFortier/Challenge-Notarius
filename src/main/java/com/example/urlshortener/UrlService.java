package com.example.urlshortener;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public List<Urls> getAllUrls(){
        return urlRepository.findAll();
    }

    public Urls getUrlsFromShort(String shortUrl) {
        try{
            System.out.println("Service : Trying to find long URL");
            return urlRepository.findUrlsByShortUrl(shortUrl).get();
        }catch(NoSuchElementException nsee){
            return new Urls("Error : No long URL found from short", shortUrl);
        }

    }

    public Urls getUrlsFromLong(String longUrl){
        try{
            System.out.println("Service : Trying to find short URL from long");
            return urlRepository.findUrlsByLongUrl(longUrl).get();
        }catch(NoSuchElementException nsee){
            return new Urls("Error : No URL found from long","Error : No URL found from long");
        }
    }

    public void saveAndShortenUrls(Urls urls) {
        if (urlRepository.findUrlsByShortUrl(urls.getShortUrl()).isEmpty()) {
            System.out.println("Service : Inserting URLs");
            urlRepository.insert(urls);
        } else {
            System.out.println("Service : Short URL already exists");
        }
    }
}
