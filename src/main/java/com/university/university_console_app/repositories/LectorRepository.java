package com.university.university_console_app.repositories;

import com.university.university_console_app.entities.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {

    Lector findByName(String name);

    List<Lector> findByNameContainingIgnoreCase(String template);
}
