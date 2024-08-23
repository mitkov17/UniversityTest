package com.university.university_console_app.sevices;

import com.university.university_console_app.entities.Degree;
import com.university.university_console_app.entities.Department;
import com.university.university_console_app.entities.Lector;
import com.university.university_console_app.repositories.DepartmentRepository;
import com.university.university_console_app.repositories.LectorRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Service
public class UniversityService {

    private final DepartmentRepository departmentRepository;
    private final LectorRepository lectorRepository;

    public UniversityService(DepartmentRepository departmentRepository, LectorRepository lectorRepository) {
        this.departmentRepository = departmentRepository;
        this.lectorRepository = lectorRepository;
    }

    public String addDepartment(String name) {
        if (departmentRepository.findByName(name) != null) {
            return "Department with name '" + name + "' already exists.";
        }

        Department department = new Department();
        department.setName(name);
        departmentRepository.save(department);
        return "Department '" + name + "' has been added.";
    }

    @Transactional
    public String addLector(String name, Degree degree, double salary, String departmentName) {
        if (lectorRepository.findByName(name) != null) {
            return "Lector with name '" + name + "' already exists.";
        }

        Department department = departmentRepository.findByName(departmentName);
        if (department == null) {
            return "Department not found.";
        }

        Hibernate.initialize(department.getLectors());

        Lector lector = new Lector();
        lector.setName(name);
        lector.setDegree(degree);
        lector.setSalary(salary);

        if (lector.getDepartments() == null) {
            lector.setDepartments(new ArrayList<>());
        }

        lector.getDepartments().add(department);
        department.getLectors().add(lector);

        lectorRepository.save(lector);
        departmentRepository.save(department);

        return "Lector '" + name + "' has been added to department '" + departmentName + "'.";
    }

    public String getHeadOfDepartment(String departmentName) {
        Department department = departmentRepository.findByName(departmentName);
        if (department != null && department.getHeadOfDepartment() != null) {
            return "Head of " + departmentName + " department is " + department.getHeadOfDepartment().getName();
        }
        return "Department not found or no head assigned";
    }

    @Transactional
    public String getDepartmentStatistics(String departmentName) {
        Department department = departmentRepository.findByName(departmentName);
        if (department != null) {
            long assistants = department.getLectors().stream()
                    .filter(lector -> lector.getDegree() == Degree.ASSISTANT).count();
            long associateProfessors = department.getLectors().stream()
                    .filter(lector -> lector.getDegree() == Degree.ASSOCIATE_PROFESSOR).count();
            long professors = department.getLectors().stream()
                    .filter(lector -> lector.getDegree() == Degree.PROFESSOR).count();

            return String.format("assistants - %d, associate professors - %d, professors - %d",
                    assistants, associateProfessors, professors);
        }
        return "Department not found";
    }

    @Transactional
    public String getAverageSalary(String departmentName) {
        Department department = departmentRepository.findByName(departmentName);
        if (department != null) {
            OptionalDouble average = department.getLectors().stream()
                    .mapToDouble(Lector::getSalary).average();
            return "The average salary of " + departmentName + " is " +
                    (average.isPresent() ? average.getAsDouble() : 0);
        }
        return "Department not found";
    }

    @Transactional
    public String getEmployeeCount(String departmentName) {
        Department department = departmentRepository.findByName(departmentName);
        if (department != null) {
            return "Employee count: " + department.getLectors().size();
        }
        return "Department not found";
    }

    public String globalSearch(String template) {
        List<Lector> lectors = lectorRepository.findByNameContainingIgnoreCase(template);
        if (lectors.isEmpty()) {
            return "No results found";
        }
        return lectors.stream().map(Lector::getName).reduce((a, b) -> a + ", " + b).orElse("");
    }

}
