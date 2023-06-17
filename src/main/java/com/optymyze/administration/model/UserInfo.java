package com.optymyze.administration.model;

import javax.persistence.*;

@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String surName;
    private String position;
    @Column(name = "github_profile")
    private String githubProfileUrl;

    public UserInfo() {
    }

    public UserInfo(String firstName, String surName, String position, String githubProfileUrl) {
        this.firstName = firstName;
        this.surName = surName;
        this.position = position;
        this.githubProfileUrl = githubProfileUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGithubProfileUrl() {
        return githubProfileUrl;
    }

    public void setGithubProfileUrl(String githubProfileUrl) {
        this.githubProfileUrl = githubProfileUrl;
    }
}
