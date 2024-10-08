package io.project.dto.users;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
}
