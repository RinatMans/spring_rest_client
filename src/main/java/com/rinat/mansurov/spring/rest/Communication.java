package com.rinat.mansurov.spring.rest;

import com.rinat.mansurov.spring.rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Communication {
    @Autowired
    private RestTemplate restTemplate = new RestTemplate();;

    String jsessionid = "";

    private final String URL = "http://91.241.64.178:7081/api/users";

    public List<User> getAllUsers() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(URL, String.class);
        jsessionid = forEntity.getHeaders().get("Set-Cookie").stream().collect(Collectors.joining());//System.out::println
        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<User>>() {
                        });

        List<User> allUsers = responseEntity.getBody();
        System.out.println(jsessionid);
        return allUsers;
    }

    public void addUser(User user) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cookie",jsessionid);

            HttpEntity<User> entity = new HttpEntity<User>(user, headers);
            ResponseEntity response =restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);

            System.out.println(response.getBody());
    }

    public void editUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie",jsessionid);

        HttpEntity<User> entity = new HttpEntity<User>(user, headers);
        ResponseEntity response =restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);

        System.out.println(response.getBody());
    }

    public void deleteUser(int id) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<User> entity = new HttpEntity<User>(headers);
        headers.set("Cookie", jsessionid);
        ResponseEntity response =restTemplate.exchange(URL+"/"+id, HttpMethod.DELETE, entity, String.class);
        System.out.println(response.getBody());
    }
}
