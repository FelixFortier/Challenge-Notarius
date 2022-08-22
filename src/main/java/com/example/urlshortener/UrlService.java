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

    /**
     * Méthode qui trouve et retourne un objet contenant l'URL long et sa version raccourcie à partir de la version raccourcie
     * Si l'URL raccourci n'existe pas, on retourne un objet contenant un message d'erreur qui pourra être affiché sur la page web
     * @param shortUrl l'URL court qui servira à la recherche
     * @return Si l'URL long existe, un Urls contenant l'URL long et sa version raccourcie. Sinon un Urls contenant un message d'erreur.
     */
    public Urls getUrlsFromShort(String shortUrl) {
        try{
            return urlRepository.findUrlsByShortUrl(shortUrl).get();
        }catch(NoSuchElementException nsee){
            return new Urls("Error : No long URL found from short", shortUrl);
        }

    }
    /**
     * Méthode qui trouve et retourne un objet contenant l'URL long et sa version raccourcie à partir de la version longue
     * Si l'URL long n'existe pas, on retourne un objet contenant un message d'erreur qui pourra être affiché sur la page web
     * @param longUrl l'URL long qui servira à la recherche
     * @return Si l'URL long existe, un Urls contenant l'URL long et sa version raccourcie. Sinon un Urls contenant un message d'erreur.
     */
    public Urls getUrlsFromLong(String longUrl){
        try{
            return urlRepository.findUrlsByLongUrl(longUrl).get();
        }catch(NoSuchElementException nsee){
            return new Urls("Error : No URL found from long","Error : No URL found from long");
        }
    }

    /**
     * Méthode qui insert un objet Urls dans la base de données s'il n'y est pas déjà présent. Autrement un message d'erreur est envoyé à la console.
     * @param urls l'objet Urls à sauvegarder dans la base de données
     */
    public void saveAndShortenUrls(Urls urls) {
        if (urlRepository.findUrlsByShortUrl(urls.getShortUrl()).isEmpty()) {
            urlRepository.insert(urls);
        } else {
            System.out.println("Service : Short URL already exists");
        }
    }
}
