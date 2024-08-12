package io.project.service;

import io.project.dto.tasks.TaskCreateDTO;
import io.project.dto.tasks.TaskDTO;
import io.project.dto.users.UserCreateDTO;
import io.project.dto.users.UserDTO;
import io.project.dto.userstasks.UserTaskDTO;
import io.project.exception.ResourceNotFoundException;
import io.project.mapper.TaskMapper;
import io.project.mapper.UserMapper;
import io.project.model.Task;
import io.project.model.User;
import io.project.repository.TaskRepository;
import io.project.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnonymizerService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;


    public UserDTO createUser() {

        UserCreateDTO dto = new UserCreateDTO();
        dto.setFirstName("GeneratedFirstName");
        dto.setLastName("GeneratedLastName");
        dto.setEmail("UsersEmail@ya.ru");
        dto.setPassword("password");

        User user = userMapper.map(dto);
        userRepository.save(user);
        return userMapper.map(user);
    }

    public TaskDTO createTask() {

        TaskCreateDTO dto = new TaskCreateDTO();
        dto.setTitle("GeneratedTitle");
        dto.setContent("GeneratedContent");
        dto.setStatus("draft");

        Task task = taskMapper.map(dto);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    public UserTaskDTO updateUserAndTask(Long userId, Long taskId) {

        User user = userRepository.findByIdWithEagerUpload(userId).orElseThrow(
                () -> new ResourceNotFoundException("User With Id: " + userId + " Not Found"));
        user.setLastName("***");

        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task With Id: " + taskId + " Not Found"));
        task.setName("***");

        user.addTask(task);
        userRepository.save(user);

        UserDTO userDTO = userMapper.map(user);
        TaskDTO taskDTO = taskMapper.map(task);

        UserTaskDTO userTaskDTO = new UserTaskDTO();
        userTaskDTO.setUserDTO(userDTO);
        userTaskDTO.setTaskDTO(taskDTO);

        return userTaskDTO;
    }
}
