package com.optymyze.administration.service.impl.impl;

import com.optymyze.administration.model.GitHubRepository;
import com.optymyze.administration.model.UserInfo;
import com.optymyze.administration.repo.UserRepository;
import com.optymyze.administration.service.GitHubService;
import com.optymyze.administration.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GitHubService gitHubService;

    public UserServiceImpl(UserRepository userRepository, GitHubService gitHubService) {
        this.userRepository = userRepository;
        this.gitHubService = gitHubService;
    }

    @Override
    public UserInfo createUser(UserInfo user) {
        return userRepository.save(user);
    }


    @Override
    public List<GitHubRepository> getUserRepositories(Long id) {
        Optional<UserInfo> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserInfo user = optionalUser.get();
            Optional<String> gitHubUsernameOptional = extractUsername(user.getGithubProfileUrl());
            if (gitHubUsernameOptional.isPresent()) {
                return gitHubService.getUserRepositories(gitHubUsernameOptional.get());
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteUser(Long id) {
        Optional<UserInfo> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public UserInfo getUserById(Long id) {
        Optional<UserInfo> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    public List<UserInfo> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserInfo updateUser(Long id, UserInfo user) {
        Optional<UserInfo> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }
    private Optional<String> extractUsername(String url) {
        // Regex pattern to match the GitHub profile URL
        String pattern = "https?://github.com/(\\w+)/?";

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(url);
        // Check if the pattern matches the URL
        if (matcher.matches()) {
            // Extract the username from the matched group
            String username = matcher.group(1);
            return Optional.ofNullable(username);
        }

        return Optional.empty();
    }
}
