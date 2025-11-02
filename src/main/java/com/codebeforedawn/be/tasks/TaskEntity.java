package com.codebeforedawn.be.tasks;

import com.codebeforedawn.be.asignees.AssigneeType;
import com.codebeforedawn.be.projects.ProjectEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    private String name;

    private String description;

    private LocalDate dueDate;

    private boolean isNextAction;

    @Enumerated(EnumType.STRING)
    private AssigneeType assigneeType;

    private long assigneeId;

    @Column(name = "completed", nullable = false)
    private boolean isCompleted;
}
