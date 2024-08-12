package io.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@ToString(includeFieldNames = true, onlyExplicitlyIncluded = true)
@Getter
@Setter
@EqualsAndHashCode(of = "email")
public class User implements BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @NotBlank
    @ToString.Include
    private String firstName;

    @NotBlank
    @ToString.Include
    private String lastName;

    @Email
    @Column(unique = true)
    @ToString.Include
    private String email;

    @Size(min = 3)
    @NotBlank
    @JsonIgnore
    private String password;
//    private String passwordDigest;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDate createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.MERGE)
    private Set<Task> tasks = new HashSet<>();

    public void addTask(Task task) {
        this.getTasks().add(task);
        task.setAssignee(this);
    }

    public void removeTask(Task task) {
        this.getTasks().remove(task);
        task.setAssignee(null);
    }
}
