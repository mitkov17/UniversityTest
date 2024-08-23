package com.university.university_console_app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "DEPARTMENT")
public class Department {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @OneToOne
    private Lector headOfDepartment;

    @ManyToMany
    private List<Lector> lectors;
}
