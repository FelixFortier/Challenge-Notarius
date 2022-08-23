package com.example.urlshortener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UrlController.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private UrlService mockUrlService;

    @Test
    void setupWindow() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/");
        MvcResult mvcResult = mockmvc.perform(request).andReturn();

        System.out.println(mvcResult.getModelAndView().getViewName());
        assertEquals("urlShortener",mvcResult.getModelAndView().getViewName());

        System.out.println(mvcResult.getModelAndView().getModelMap().getAttribute("urlList"));
        assertNotNull(mvcResult.getModelAndView().getModelMap().getAttribute("urlList"));
        assertNull(mvcResult.getModelAndView().getModelMap().getAttribute("attributeThatDoesntExist"));
    }

    @Test
    void shortenUrls() throws Exception {
        Urls urlToPost = new Urls("www.testUrl.com", null);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/shortenUrls")
                .flashAttr("urls", urlToPost);

        MvcResult mvcResult = mockmvc.perform(request).andReturn();
        String url = mvcResult.getModelAndView().getModelMap().getAttribute("shortenedURL").toString();
        System.out.println(url);
        assertEquals("surl.com/", url.substring(0,9));
        assertEquals(19,url.length());

    }

    /*@Test
    void getLongUrl() throws Exception {
        Urls urlToGet = new Urls("www.testUrl.com", "shortUrl");
        mockUrlService.saveUrls(urlToGet);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/getLongUrl")
                .flashAttr("urls", urlToGet);

        MvcResult mvcResult = mockmvc.perform(request).andReturn();
        assertNotNull(mvcResult.getModelAndView().getModelMap().getAttribute("foundLongURL"));
    }*/
}