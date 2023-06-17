package com.optymyze.administration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optymyze.administration.model.GitHubRepository;
import com.optymyze.administration.model.UserInfo;
import com.optymyze.administration.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testCreateUser() throws Exception {

        UserInfo user = new UserInfo();
        user.setFirstName("Hafiz");

        // Mock the userService.createUser() method
        UserInfo savedUser = new UserInfo();
        savedUser.setId(1L);
        savedUser.setFirstName("Hafiz");
        given(userService.createUser(any(UserInfo.class))).willReturn(savedUser);

        // Perform the POST request to create a user
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.firstName").value(savedUser.getFirstName()));
    }


    @Test
    public void testGetAllUsers() throws Exception {
        UserInfo user1 = new UserInfo();
        user1.setId(1L);
        user1.setFirstName("Hafiz");
        user1.setSurName("Usman");

        UserInfo user2 = new UserInfo();
        user2.setId(2L);
        user2.setFirstName("Ali");
        user2.setSurName("Usman");

        List<UserInfo> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()));
    }


    @Test
    public void getUserByWithUnavailableIdTest() throws Exception {

        mockMvc.perform(get("/users/{id}", 15)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void updateUserWithUnavailableIdTest() throws Exception {
        // Set up the initial user data
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setFirstName("Hafiz");
        userInfo.setSurName("Usman");

        // Perform the update operation
        mockMvc.perform(put("/users/{id}", 12)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userInfo)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteUserWithUnavailableIdTest() throws Exception {
        mockMvc.perform(delete("/users/{id}", 15)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUserRepositories() throws Exception {
        // Create a sample user
        UserInfo user = new UserInfo();
        user.setId(1L);
        user.setGithubProfileUrl("https://github.com/hafizmuhammadusman");

        // Mock the userService.getUserRepositories() method
        List<GitHubRepository> repositories = Arrays.asList(
                new GitHubRepository("repo1", "Java"),
                new GitHubRepository("repo2", "Python")
        );
        given(userService.getUserRepositories(eq(1L))).willReturn(repositories);

        // Perform the GET request to retrieve user repositories
        mockMvc.perform(get("/users/{id}/repositories", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("repo1"))
                .andExpect(jsonPath("$[0].language").value("Java"))
                .andExpect(jsonPath("$[1].name").value("repo2"))
                .andExpect(jsonPath("$[1].language").value("Python"));

        // Verify that the userService.getUserRepositories() method was called
        verify(userService, times(1)).getUserRepositories(eq(1L));
    }

    private String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
