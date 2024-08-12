package io.project.controller;

import io.project.dto.tasks.TaskCreateDTO;
import io.project.dto.tasks.TaskDTO;
import io.project.dto.users.UserCreateDTO;
import io.project.dto.users.UserDTO;
import io.project.dto.userstasks.UserTaskDTO;
import org.springframework.http.MediaType;
import io.project.mapper.TaskMapper;
import io.project.mapper.UserMapper;
import io.project.model.Task;
import io.project.model.User;
import io.project.repository.TaskRepository;
import io.project.repository.UserRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AnonymizerControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TaskMapper taskMapper;

    private User testUser;

    private Task testTask;

    @BeforeEach
    public void setUp() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setFirstName("Aleksandr");
        userCreateDTO.setLastName("Pushkin");
        userCreateDTO.setEmail("ap@gmail.com");
        userCreateDTO.setPassword("test_password");
        testUser = userMapper.map(userCreateDTO);
        userRepository.save(testUser);
        System.out.println("User 1: " + testUser);

        TaskCreateDTO taskCreateDTO = new TaskCreateDTO();
        taskCreateDTO.setTitle("TestTitle");
        taskCreateDTO.setContent("TestContent");
        taskCreateDTO.setStatus("draft");
        taskCreateDTO.setAssigneeId(testUser.getId());
        testTask = taskMapper.map(taskCreateDTO);
        taskRepository.save(testTask);
        System.out.println("Task: " + testTask);

        testUser.addTask(testTask);
        userRepository.save(testUser);
        System.out.println("User with tasks: " + testUser);
    }

    @AfterEach
    public void clean() {
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testUpdate() throws Exception {
        UserDTO userDTO = userMapper.map(testUser);
        TaskDTO taskDTO = taskMapper.map(testTask);
        UserTaskDTO userTaskDTO = new UserTaskDTO();
        userTaskDTO.setUserDTO(userDTO);
        userTaskDTO.setTaskDTO(taskDTO);

        var request = put("/api/user/" + testUser.getId() + "/task/" + testTask.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(userTaskDTO));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var user = userRepository.findByIdWithEagerUpload(testUser.getId()).orElseThrow();
        var task = taskRepository.findById(testTask.getId()).orElseThrow();

        assertThat(user.getFirstName()).isEqualTo(userTaskDTO.getUserDTO().getFirstName());
        assertThat(user.getLastName()).isEqualTo("***");
        assertThat(task.getName()).isEqualTo("***");
    }

    @Test
    public void testUpdateUserAndTask() throws Exception {

        mockMvc.perform(put("/api/user/" + testUser.getId() + "/task/" + testTask.getId()))
                .andExpect(status().isOk());

        User user = userRepository.findById(testUser.getId()).orElseThrow(
                () -> new RuntimeException("User with id " + testUser.getId() + "not found"));
        Task task = taskRepository.findById(testTask.getId()).orElseThrow(
                () -> new RuntimeException("Task with id " + testTask.getId() + "not found"));

        assertThat(user.getLastName()).isEqualTo("***");
        assertThat(task.getName()).isEqualTo("***");
    }
}
