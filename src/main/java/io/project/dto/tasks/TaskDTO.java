package io.project.dto.tasks;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TaskDTO {

    private long id;
    private Integer index;

    @JsonProperty("assignee_id")
    private long assigneeId;

    private String title;
    private String content;
    private String status;
}
