package io.project.controller;

import io.project.dto.tasks.TaskCreateDTO;
import io.project.dto.users.UserCreateDTO;
import io.project.service.AnonymizerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AnonymizerController {
    private final AnonymizerService anonymizerService;


    @GetMapping("/create/user")
    public ResponseEntity<UserCreateDTO> createUser() {
        return ResponseEntity.ok(anonymizerService.createUser());
    }

    @GetMapping("/create/task")
    public ResponseEntity<TaskCreateDTO> createTask() {
        return ResponseEntity.ok(anonymizerService.createTask());
    }

    @GetMapping("/update/user/{userId}/task/{taskId}")
    public ResponseEntity<Void> updateUserAndTask(@PathVariable Long userId,
                                                  @PathVariable Long taskId) {

        anonymizerService.updateUserAndTask(userId, taskId);
        return ResponseEntity.ok().build();
    }
}
