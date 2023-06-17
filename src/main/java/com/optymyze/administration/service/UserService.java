package com.optymyze.administration.service;

import com.optymyze.administration.model.GitHubRepository;
import com.optymyze.administration.model.UserInfo;

import java.util.List;

public interface UserService {

    UserInfo createUser(UserInfo user);

    List<UserInfo> getAllUsers();

    UserInfo getUserById(Long id);

    UserInfo updateUser(Long id, UserInfo user);

    void deleteUser(Long id);

    List<GitHubRepository> getUserRepositories(Long id);

}
