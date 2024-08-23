package com.university.university_console_app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "LECTOR")
public class Lector {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Enumerated(EnumType.STRING)
    private Degree degree;

    private Double salary;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "lectors")
    private List<Department> departments = new ArrayList<>();
}
