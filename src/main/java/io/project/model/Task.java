package io.project.model;

import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "tasks")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@EqualsAndHashCode(of = {"name", "status"})
public class Task implements BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @NotBlank
    @Size(min = 1)
    private String name;

    private Integer index;

    @NotBlank
    private String description;

    @CreatedDate
    private LocalDate createdAt;

    @NotBlank
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User assignee;
}
