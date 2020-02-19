package com.github.perscholas;

import com.github.perscholas.dao.StudentDao;
import com.github.perscholas.service.CourseService;
import com.github.perscholas.service.StudentService;
import com.github.perscholas.utils.IOConsole;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SchoolManagementSystem implements Runnable {
    private static final IOConsole console = new IOConsole();

    @Override
    public void run() {
        String smsDashboardInput = getSchoolManagementSystemDashboardInput();
        if ("login".equals(smsDashboardInput)) {
            StudentDao studentService = new StudentService(DatabaseConnection.MARIADB); // TODO - get literal value
            String studentEmail = console.getStringInput("Enter your email:");
            String studentPassword = console.getStringInput("Enter your password:");
            Boolean isValidLogin = null;
            try {
                isValidLogin = studentService.validateStudent(studentEmail, studentPassword);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (isValidLogin) {
                String studentDashboardInput = getStudentDashboardInput();
                if ("register".equals(studentDashboardInput)) {
                    Integer courseId = null;
                    try {
                        courseId = getCourseRegistryInput();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    studentService.registerStudentToCourse(studentEmail, courseId);
                }
            }
        }
    }

    private String getSchoolManagementSystemDashboardInput() {
        return console.getStringInput(new StringBuilder()
                .append("Welcome to the School Management System Dashboard!")
                .append("\nFrom here, you can select any of the following options:")
                .append("\n\t[ login ], [ logout ]")
                .toString());
    }

    private String getStudentDashboardInput() {
        return console.getStringInput(new StringBuilder()
                .append("Welcome to the Student Dashboard!")
                .append("\nFrom here, you can select any of the following options:")
                .append("\n\t[ register ], [ logout]")
                .toString());
    }


    private Integer getCourseRegistryInput() throws SQLException {
        CourseService courseService = new CourseService();
        List<Integer> listOfCoursesIds = courseService.getAllCourses().stream().map(c -> c.getId()).collect(Collectors.toList());

        return console.getIntegerInput(new StringBuilder()
                .append("Welcome to the Course Registration Dashboard!")
                .append("\nFrom here, you can select any of the following options:")
                .append("\n\t" + listOfCoursesIds.toString())
                .toString());
    }
}
