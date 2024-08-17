package io.project.service;

import io.project.dto.tasks.TaskCreateDTO;
import io.project.dto.tasks.TaskDTO;
import io.project.dto.users.UserCreateDTO;
import io.project.dto.users.UserDTO;
import io.project.dto.userstasks.UserTaskDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@AllArgsConstructor
public class AnonymizerService {
    private final RestTemplate restTemplate;


    public UserCreateDTO createUser() {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setFirstName("GeneratedFirstName");
        dto.setLastName("GeneratedLastName");
        dto.setEmail("UsersEmail@ya.ru");
        dto.setPassword("password");
        return restTemplate.postForObject(
                "http://localhost:8080/create/user", dto, UserCreateDTO.class);
    }

    public TaskCreateDTO createTask() {
        TaskCreateDTO dto = new TaskCreateDTO();
        dto.setTitle("GeneratedTitle");
        dto.setContent("GeneratedContent");
        dto.setStatus("draft");
        return restTemplate.postForObject(
                "http://localhost:8080/create/task", dto, TaskCreateDTO.class);
    }

    public UserDTO getUserById(Long id) {
        return restTemplate.getForObject(
                "http://localhost:8080/users/{id}", UserDTO.class, id);
    }

    public TaskDTO getTaskById(Long id) {
        return restTemplate.getForObject(
                "http://localhost:8080/tasks/{id}", TaskDTO.class, id);
    }

    public void updateUserAndTask(Long userId, Long taskId) {
        // get user
        UserDTO userDTO = getUserById(userId);
        // get task
        TaskDTO taskDTO = getTaskById(taskId);
        // set ***
        userDTO.setLastName("***");
        taskDTO.setTitle("***");

        UserTaskDTO userTaskDTO = new UserTaskDTO();
        userTaskDTO.setUserDTO(userDTO);
        userTaskDTO.setTaskDTO(taskDTO);
        // update
        restTemplate.put("http://localhost:8080/update/usertask", userTaskDTO);

        ResponseEntity.ok().build();
    }
}
