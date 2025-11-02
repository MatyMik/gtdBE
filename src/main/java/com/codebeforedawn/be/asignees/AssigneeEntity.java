package com.codebeforedawn.be.asignees;

import jakarta.persistence.*;

@Entity
@Table (name = "assignees")
public class AssigneeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
