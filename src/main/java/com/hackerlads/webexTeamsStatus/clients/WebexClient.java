package com.hackerlads.webexTeamsStatus.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class WebexClient {

    private Environment env;

    private static final String MSG_URL = "https://api.ciscospark.com/v1/messages";

    public WebexClient(Environment env) {
        this.env = env;
    }

    public Object getMessageDetails(String messageId) {
        String getMessageDetailsURL = MSG_URL + "/"+ messageId;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(getMessageDetailsURL, HttpMethod.GET, makeHTTPEntity(null), Object.class);
            System.out.println("Successfully Retrieved Message ID: " + responseEntity.getBody());
        } catch (Exception e) {
            System.out.println("Error Retrieving Message ID: " + e.getMessage());
        }

        if (null != responseEntity) {
            LinkedHashMap mappedMsg = (LinkedHashMap) responseEntity.getBody();
            if (mappedMsg != null) {
                return mappedMsg.get("html");
            }
        }
        return "Object Not Retrieved";
    }

    public void createMessage(String msgContent) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("roomId", env.getProperty("webexRoomId"));
        body.add("text", msgContent);

        try {
            ResponseEntity<Object> response = restTemplate.exchange(MSG_URL, HttpMethod.POST, makeHTTPEntity(body), Object.class);
            System.out.println("Successfully Created Message: " + response.getBody());
        } catch (Exception e) {
            System.out.println("Error Creating Message: " + e.getMessage());
        }
    }

    private HttpEntity<Object> makeHTTPEntity(MultiValueMap body) {

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(mediaTypes);
        httpHeaders.setBearerAuth(env.getProperty("webexAuthHeaderToken"));
        return new HttpEntity<>(body, httpHeaders);
    }
}
