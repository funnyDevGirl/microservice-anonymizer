package io.project.dto.userstasks;

import io.project.dto.tasks.TaskDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWithTaskDTO {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private TaskDTO taskDTO;
}
