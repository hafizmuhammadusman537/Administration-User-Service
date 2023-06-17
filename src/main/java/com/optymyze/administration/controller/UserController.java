package com.optymyze.administration.controller;

import com.optymyze.administration.model.GitHubRepository;
import com.optymyze.administration.model.UserInfo;
import com.optymyze.administration.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController implements UserApis {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserInfo> createUser(UserInfo user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @Override
    public ResponseEntity<List<UserInfo>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Override
    public ResponseEntity<UserInfo> getUserById(Long id) {
        UserInfo user = userService.getUserById(id);
        if (Objects.nonNull(user)) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<UserInfo> updateUser(Long id, UserInfo userInfo) {
        UserInfo user = userService.getUserById(id);
        if (Objects.nonNull(user)) {
            return ResponseEntity.ok(userService.updateUser(id, userInfo));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        UserInfo user = userService.getUserById(id);
        if (Objects.nonNull(user)) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<GitHubRepository>> getUserRepositories(Long id) {

        List<GitHubRepository> userRepositories = userService.getUserRepositories(id);
        if (!userRepositories.isEmpty()) {
            return ResponseEntity.ok(userRepositories);
        }
        return ResponseEntity.notFound().build();
    }

}
