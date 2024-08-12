package io.project.dto.userstasks;

import lombok.Getter;
import lombok.Setter;
import io.project.dto.users.UserDTO;
import io.project.dto.tasks.TaskDTO;

@Getter
@Setter
public class UserTaskDTO {
    private UserDTO userDTO;
    private TaskDTO taskDTO;
}
