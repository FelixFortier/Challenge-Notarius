package com.example.urlshortener;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlRepository extends MongoRepository<Urls,String> {

    Optional<Urls> findUrlsByShortUrl(String shortUrl);
    Optional<Urls> findUrlsByLongUrl(String longUrl);


}
