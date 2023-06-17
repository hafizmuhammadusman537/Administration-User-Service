package com.optymyze.administration.service;

import com.optymyze.administration.model.GitHubRepository;

import java.util.List;

public interface GitHubService {
    List<GitHubRepository> getUserRepositories(String username);
}
