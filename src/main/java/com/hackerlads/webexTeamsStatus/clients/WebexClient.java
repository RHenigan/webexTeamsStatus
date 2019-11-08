package com.hackerlads.webexTeamsStatus.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class WebexClient {

    @Autowired
    Environment env;

    public Object getMessageDetails(String messageId) {
        String getMessageDetailsURL = "https://api.ciscospark.com/v1/messages/" + messageId;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(getMessageDetailsURL, HttpMethod.GET, makeHTTPEntity(), Object.class);
            System.out.println("Successfully Retrieved Message ID");
        } catch (Exception e) {
            System.out.println("Error Retrieving Message ID" + e.getMessage());
        }

        if (null != responseEntity) {
            LinkedHashMap mappedMsg = (LinkedHashMap) responseEntity.getBody();
            if (mappedMsg != null) {
                return mappedMsg.get("html");
            }
        }
        return "Object Not Retrieved";
    }

    private HttpEntity<Object> makeHTTPEntity() {

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(mediaTypes);
        httpHeaders.setBearerAuth(env.getProperty("webexAuthHeaderToken"));
        return new HttpEntity<>(null, httpHeaders);
    }
}
