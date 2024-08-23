package com.university.university_console_app.sevices;

import com.university.university_console_app.entities.Degree;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class UniversityCommands {

    private final UniversityService universityService;

    public UniversityCommands(UniversityService universityService) {
        this.universityService = universityService;
    }

    @ShellMethod(value = "Add a new department", key = "Add department")
    public String addDepartment(@ShellOption String name) {
        return universityService.addDepartment(name);
    }

    @ShellMethod(value = "Add a new lector", key = "Add lector")
    public String addLector(@ShellOption String name,
                            @ShellOption Degree degree,
                            @ShellOption double salary,
                            @ShellOption String departmentName) {
        return universityService.addLector(name, degree, salary, departmentName);
    }

    @ShellMethod(value = "Get head of department by name", key = "Who is head of department")
    public String whoIsHeadOfDepartment(@ShellOption String departmentName) {
        return universityService.getHeadOfDepartment(departmentName);
    }

    @ShellMethod(value = "Show department statistics", key = "Show statistics")
    public String showDepartmentStatistics(@ShellOption String departmentName) {
        return universityService.getDepartmentStatistics(departmentName);
    }

    @ShellMethod(value = "Show average salary for department", key = "Show the average salary for the department")
    public String showAverageSalary(@ShellOption String departmentName) {
        return universityService.getAverageSalary(departmentName);
    }

    @ShellMethod(value = "Show employee count for department", key = "Show count of employee for")
    public String showEmployeeCount(@ShellOption String departmentName) {
        return universityService.getEmployeeCount(departmentName);
    }

    @ShellMethod(value = "Global search by template", key = "Global search by")
    public String globalSearchByTemplate(@ShellOption String template) {
        return universityService.globalSearch(template);
    }
}
