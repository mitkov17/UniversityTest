package com.university.university_console_app.services;

import com.university.university_console_app.entities.Degree;
import com.university.university_console_app.entities.Department;
import com.university.university_console_app.entities.Lector;
import com.university.university_console_app.repositories.DepartmentRepository;
import com.university.university_console_app.repositories.LectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UniversityServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private LectorRepository lectorRepository;

    @InjectMocks
    private UniversityService universityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddDepartmentSuccess() {
        when(departmentRepository.findByName("Physics")).thenReturn(null);

        String result = universityService.addDepartment("Physics");

        assertEquals("Department 'Physics' has been added.", result);
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    public void testAddDepartmentAlreadyExists() {
        Department existingDepartment = new Department();
        existingDepartment.setName("Physics");
        when(departmentRepository.findByName("Physics")).thenReturn(existingDepartment);

        String result = universityService.addDepartment("Physics");

        assertEquals("Department with name 'Physics' already exists.", result);
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    public void testAddLectorSuccess() {
        Department department = new Department();
        department.setName("Physics");
        department.setLectors(new ArrayList<>());

        when(departmentRepository.findByName("Physics")).thenReturn(department);

        String result = universityService.addLector("John Doe", Degree.ASSISTANT, 50000, "Physics");

        assertEquals("Lector 'John Doe' has been added to department 'Physics'.", result);
        verify(lectorRepository, times(1)).save(any(Lector.class));
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    public void testAddLectorDepartmentNotFound() {
        when(departmentRepository.findByName("NonExistentDepartment")).thenReturn(null);

        String result = universityService.addLector("John Doe", Degree.ASSISTANT, 50000, "NonExistentDepartment");

        assertEquals("Department not found.", result);
        verify(lectorRepository, never()).save(any(Lector.class));
    }

    @Test
    public void testGetHeadOfDepartment() {
        Lector lector = new Lector();
        lector.setName("John Doe");

        Department department = new Department();
        department.setName("Physics");
        department.setHeadOfDepartment(lector);

        when(departmentRepository.findByName("Physics")).thenReturn(department);

        String result = universityService.getHeadOfDepartment("Physics");
        assertEquals("Head of Physics department is John Doe", result);
    }

    @Test
    public void testGetDepartmentStatistics() {
        Lector assistant = new Lector();
        assistant.setDegree(Degree.ASSISTANT);

        Lector associateProfessor = new Lector();
        associateProfessor.setDegree(Degree.ASSOCIATE_PROFESSOR);

        Lector professor = new Lector();
        professor.setDegree(Degree.PROFESSOR);

        Department department = new Department();
        department.setName("Physics");
        department.setLectors(Arrays.asList(assistant, associateProfessor, professor));

        when(departmentRepository.findByName("Physics")).thenReturn(department);

        String result = universityService.getDepartmentStatistics("Physics");
        assertEquals("assistants - 1, associate professors - 1, professors - 1", result);
    }

    @Test
    public void testGetAverageSalary() {
        Lector lector1 = new Lector();
        lector1.setSalary(50000.0);

        Lector lector2 = new Lector();
        lector2.setSalary(100000.0);

        Department department = new Department();
        department.setName("Physics");
        department.setLectors(Arrays.asList(lector1, lector2));

        when(departmentRepository.findByName("Physics")).thenReturn(department);

        String result = universityService.getAverageSalary("Physics");
        assertEquals("The average salary of Physics is 75000.0", result);
    }

    @Test
    public void testGetEmployeeCount() {
        Lector lector1 = new Lector();
        Lector lector2 = new Lector();

        Department department = new Department();
        department.setName("Physics");
        department.setLectors(Arrays.asList(lector1, lector2));

        when(departmentRepository.findByName("Physics")).thenReturn(department);

        String result = universityService.getEmployeeCount("Physics");
        assertEquals("Employee count: 2", result);
    }

    @Test
    public void testGlobalSearch() {
        Lector lector1 = new Lector();
        lector1.setName("John Doe");

        Lector lector2 = new Lector();
        lector2.setName("Jane Doe");

        List<Lector> lectors = Arrays.asList(lector1, lector2);
        when(lectorRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(lectors);

        String result = universityService.globalSearch("Doe");
        assertEquals("John Doe, Jane Doe", result);
    }

    @Test
    public void testGlobalSearchNoResults() {
        when(lectorRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(Collections.emptyList());

        String result = universityService.globalSearch("NonExistentName");
        assertEquals("No results found", result);
    }
}
