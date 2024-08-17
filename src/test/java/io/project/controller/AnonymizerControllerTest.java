package io.project.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import io.project.dto.tasks.TaskCreateDTO;
import io.project.dto.users.UserCreateDTO;
import io.project.service.AnonymizerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnonymizerControllerTest {

    @Autowired
    private AnonymizerService anonymizerService;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testCreateUser() {
        mockServer.expect(once(), requestTo("http://localhost:8080/create/user"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"firstName\":\"GeneratedFirstName\","
                                + "\"lastName\":\"GeneratedLastName\","
                                + "\"email\":\"UsersEmail@ya.ru\"}",
                        MediaType.APPLICATION_JSON));

        UserCreateDTO userCreateDTO = anonymizerService.createUser();
        assertThat(userCreateDTO).isNotNull();
        assertThat(userCreateDTO.getFirstName()).isEqualTo("GeneratedFirstName");

        mockServer.verify();
    }

    @Test
    public void testCreateTask() {
        mockServer.expect(once(), requestTo("http://localhost:8080/create/task"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"title\":\"GeneratedTitle\","
                                + "\"content\":\"GeneratedContent\","
                                + "\"status\":\"draft\"}",
                        MediaType.APPLICATION_JSON));

        TaskCreateDTO taskCreateDTO = anonymizerService.createTask();
        assertThat(taskCreateDTO).isNotNull();
        assertThat(taskCreateDTO.getTitle()).isEqualTo("GeneratedTitle");

        mockServer.verify();
    }

    @Test
    public void testUpdateUserAndTask() {
        Long userId = 1L;
        Long taskId = 1L;

        mockServer.expect(once(), requestTo("http://localhost:8080/users/" + userId))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"id\":1,\"lastName\":\"UserLastName\"}",
                        MediaType.APPLICATION_JSON));

        mockServer.expect(once(), requestTo("http://localhost:8080/tasks/" + taskId))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"id\":1,\"title\":\"TaskTitle\"}",
                        MediaType.APPLICATION_JSON));

        mockServer.expect(once(), requestTo("http://localhost:8080/update/usertask"))
                .andExpect(method(PUT))
                .andRespond(withSuccess());

        anonymizerService.updateUserAndTask(userId, taskId);

        mockServer.verify();
    }
}
