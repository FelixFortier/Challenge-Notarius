package com.example.urlshortener;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class UrlController {
    private final UrlService urlService;

    /**
     * Méthode qui permet l'affichage de la table des URLs à la première ouverture de la page web
     * @return le modèle et la vue à afficher
     */
    @GetMapping()
    public ModelAndView setupWindow(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("urlShortener");
        modelAndView.addObject("urlList",urlService.getAllUrls());
        return modelAndView;
    }

    /**
     * Méthode qui enregistre un URL avec sa version raccourcie dans la base de données
     * ou retourne un URL court déjà existant
     * @param urls Objet contenant l'URL long
     * @return le modèle et la vue à afficher
     */
    @RequestMapping("/shortenUrls")
    public ModelAndView shortenUrls(@ModelAttribute Urls urls){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("urlShortener");

        String trimmedlongurl = trimURL(urls.getLongUrl());
        urls.setLongUrl(trimmedlongurl);

        if(urlService.getUrlsFromLong(trimmedlongurl).getLongUrl().equals("Error : No URL found from long")){
            String shortenedUrl = "surl.com/"+generateBase62Id();
            urls.setShortUrl(shortenedUrl);
            urlService.saveAndShortenUrls(urls);
            modelAndView.addObject("shortenedURL",urlService.getUrlsFromShort(urls.getShortUrl()).getShortUrl());
            modelAndView.addObject("urlList",urlService.getAllUrls());
        }else{
            modelAndView.addObject("shortenedURL","Already exists : " + urlService.getUrlsFromLong(urls.getLongUrl()).getShortUrl());
            modelAndView.addObject("urlList",urlService.getAllUrls());
        }

        return modelAndView;
    }

    /**
     * Méthode servant à retirer les préfixes communs à beaucoup d'URLs (https et www)
     * Permet d'éviter que deux URLs menant au même site soit entrés dans la base de données comme deux URLs distincts.
     * @param url L'URL à épurer
     * @return trimmedlongurl L'URL épuré
     */
    private String trimURL(String url){
        String trimmedlongurl = url+"";
        trimmedlongurl = trimmedlongurl.replace("http://", "");
        trimmedlongurl = trimmedlongurl.replace("https://", "");
        trimmedlongurl = trimmedlongurl.replace("www.", "");
        return trimmedlongurl;

    }



    /**
     * Méthode qui gére l'affichage d'un URL original à partir de sa version raccourcie
     * @param urls Objet contenant l'URL court
     * @return le modèle et la vue à afficher
     */
    @RequestMapping("/getLongUrl")
    public ModelAndView getLongUrl(@ModelAttribute Urls urls){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("urlShortener");
        modelAndView.addObject("foundLongURL",urlService.getUrlsFromShort(urls.getShortUrl()).getLongUrl());
        modelAndView.addObject("urlList",urlService.getAllUrls());

        return modelAndView;
    }

    /**
     * Méthode pour générer aléatoirement la clé de 10 caractères en base62 qui suivra le domaine de l'url
     * @return la clé générée
     */
    private String generateBase62Id() {
        int max = 61;
        int min = 0;
        int range = max - min + 1;

        String base62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String id = "";
        for (int i = 0; i < 10; i++) {
            int randomIndex = (int) (Math.random() * range) + min;
            id += base62.charAt(randomIndex);
        }
        System.out.println("Created new url id : " + id);
        return id;
    }

}
