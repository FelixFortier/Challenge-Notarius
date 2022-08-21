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

    @GetMapping()
    public ModelAndView setupWindow(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("urlShortener");
        modelAndView.addObject("urlList",urlService.getAllUrls());
        return modelAndView;
    }
    @RequestMapping("/shortenUrls")
    public ModelAndView shortenUrls(@ModelAttribute Urls urls){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("urlShortener");

        System.out.println(urls.getLongUrl());
        String trimmedlongurl = urls.getLongUrl();
        trimmedlongurl = trimmedlongurl.replace("http://", "");
        trimmedlongurl = trimmedlongurl.replace("https://", "");
        trimmedlongurl = trimmedlongurl.replace("www.", "");
        urls.setLongUrl(trimmedlongurl);
        System.out.println(trimmedlongurl);

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
    @RequestMapping("/getLongUrl")
    public ModelAndView getLongUrl(@ModelAttribute Urls urls){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("urlShortener");
        modelAndView.addObject("foundLongURL",urlService.getUrlsFromShort(urls.getShortUrl()).getLongUrl());
        modelAndView.addObject("urlList",urlService.getAllUrls());

        return modelAndView;
    }
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
