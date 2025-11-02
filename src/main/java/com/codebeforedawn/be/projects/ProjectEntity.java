package com.codebeforedawn.be.projects;

import com.codebeforedawn.be.areas.AreaEntity;
import com.codebeforedawn.be.asignees.AssigneeType;
import com.codebeforedawn.be.common.entity.AuditableEntity;
import com.codebeforedawn.be.tasks.TaskEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="projects")
@Getter
@Setter
public class ProjectEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "area_id", nullable = false)
    private AreaEntity area;

    private String name;

    private String description;

    private boolean isActive;

    @Enumerated(EnumType.STRING)
    private AssigneeType assigneeType;
    private long assigneeId;
    private boolean completed;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TaskEntity> tasks;
}