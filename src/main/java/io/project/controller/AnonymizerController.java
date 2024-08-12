package io.project.controller;

import io.project.dto.tasks.TaskDTO;
import io.project.dto.users.UserDTO;
import io.project.dto.userstasks.UserTaskDTO;
import io.project.service.AnonymizerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AnonymizerController {
    private final AnonymizerService anonymizerService;


    @GetMapping("/create/user")
    public ResponseEntity<UserDTO> createUser() {
        UserDTO user = anonymizerService.createUser();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/create/task")
    public ResponseEntity<TaskDTO> createTask() {
        TaskDTO task = anonymizerService.createTask();
        return ResponseEntity.ok(task);
    }

    @PutMapping("/user/{userId}/task/{taskId}")
    public ResponseEntity<UserTaskDTO> updateUserAndTask(@PathVariable Long userId,
                                                         @PathVariable Long taskId) {

        UserTaskDTO userTaskDTO = anonymizerService.updateUserAndTask(userId, taskId);
        return ResponseEntity.ok(userTaskDTO);
    }

//    @PutMapping("/user/{userId}/task/{taskId}")
//    public ResponseEntity<?> updateUserAndTask(@PathVariable Long userId,
//                                               @PathVariable Long taskId) {
//
//        anonymizerService.updateUserAndTask(userId, taskId);
//        return ResponseEntity.noContent().build();
//    }
}
