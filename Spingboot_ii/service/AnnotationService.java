package hy452.ws.spring_client.service;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;


@Service
public class AnnotationService {
	private static final String API_URL = "https://api.dbpedia-spotlight.org/en/annotate";

    public String call_DBpedia(String inputText) throws Exception {
        RestTemplate restTemplate = new RestTemplate(); /*a client in Spring that lets your application make HTTP requests to other services.*/
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded"));
        headers.set("Accept", "application/json");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("text", inputText); /*post body for DBpedia*/

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(API_URL, request, String.class);

        return response.getBody(); /*json with the infos*/
    }
}
