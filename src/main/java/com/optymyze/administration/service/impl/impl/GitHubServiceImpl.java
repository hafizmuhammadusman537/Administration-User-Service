package com.optymyze.administration.service.impl.impl;

import com.optymyze.administration.model.GitHubRepository;
import com.optymyze.administration.service.GitHubService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GitHubServiceImpl implements GitHubService {

    private static final String GITHUB_API_URL = "https://api.github.com";

    private final RestTemplate restTemplate;
    private final String authToken; // configurable auth token for GitHub API

    public GitHubServiceImpl(RestTemplateBuilder restTemplateBuilder, @Value("${github.auth.token}") String authToken) {

        this.restTemplate = restTemplateBuilder.build();
        this.authToken = authToken;
    }

    public List<GitHubRepository> getUserRepositories(String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);

        String url = GITHUB_API_URL + "/users/" + username + "/repos";
        try {
            ResponseEntity<GitHubRepository[]> response = restTemplate.exchange(
                    url, HttpMethod.GET, new HttpEntity<>(headers), GitHubRepository[].class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return Arrays.asList(response.getBody());
            } else {
                throw new RuntimeException("Failed to retrieve user repositories from GitHub API");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve user repositories from GitHub API");
        }
    }
}
