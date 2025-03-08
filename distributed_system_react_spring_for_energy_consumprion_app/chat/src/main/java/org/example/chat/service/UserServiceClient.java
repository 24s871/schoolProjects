package org.example.chat.service;

import org.example.chat.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceClient {
    private final RestTemplate restTemplate;
    private final String userServiceUrl = "http://localhost:8083";

    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getUsersByIds(List<Integer> userIds) {
        String url = userServiceUrl + "/chat/users";
       // HttpEntity<List<Integer>> request = new HttpEntity<>(userIds);
        ResponseEntity<List<User>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<List<User>>() {},
                userIds
        );
        return response.getBody();
    }

    public List<Integer> findAllChatUsers(Integer currentUserId) {
        String url = userServiceUrl + "/api/users/chatUsers/{currentUserId}";
        return restTemplate.getForObject(url, List.class, currentUserId);
    }

}
