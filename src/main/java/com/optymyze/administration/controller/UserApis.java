package com.optymyze.administration.controller;

import com.optymyze.administration.model.GitHubRepository;
import com.optymyze.administration.model.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
public interface UserApis {

    @PostMapping
    ResponseEntity<UserInfo> createUser(@RequestBody UserInfo user);

    @GetMapping
    ResponseEntity<List<UserInfo>> getAllUsers();

    @GetMapping("/{id}")
    ResponseEntity<UserInfo> getUserById(@PathVariable("id") Long id);

    @PutMapping("/{id}")
    ResponseEntity<UserInfo> updateUser(@PathVariable("id") Long id, @RequestBody UserInfo userInfo);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable("id") Long id);

    @GetMapping("/{id}/repositories")
    ResponseEntity<List<GitHubRepository>> getUserRepositories(@PathVariable("id") Long id);

}
