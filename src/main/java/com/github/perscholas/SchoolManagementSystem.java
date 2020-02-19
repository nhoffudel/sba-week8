package com.github.perscholas;

import com.github.perscholas.dao.CourseDao;
import com.github.perscholas.model.CourseInterface;
import com.github.perscholas.service.CourseService;
import com.github.perscholas.service.StudentService;
import com.github.perscholas.utils.IOConsole;
import java.util.*;

public class SchoolManagementSystem implements Runnable {
    private static final IOConsole console = new IOConsole();

    @Override
    public void run() {
        CourseDao courseService = new CourseService(DatabaseConnection.MANAGEMENT_SYSTEM);
        List<Integer> listOfCoursesIds = ((CourseService) courseService).getAllCourseIDs();
        while (true) {
            String smsDashboardInput = getSchoolManagementSystemDashboardInput();
            if ("login".equals(smsDashboardInput)) {
                StudentService studentService = new StudentService();
                String studentEmail = console.getStringInput("Enter your email:");
                String studentPassword = console.getStringInput("Enter your password:");
                Boolean isValidLogin = studentService.validateStudent(studentEmail, studentPassword);
                if (isValidLogin) {
                    boolean loggedIn = true;
                    while (loggedIn) {
                        String name = studentService.getStudentByEmail(studentEmail).getName();
                        System.out.println(name + " is registered for these courses: ");
                        List<CourseInterface> courses = studentService.getStudentCourses(studentEmail);
                        for (CourseInterface c : courses)
                            System.out.println("Course ID: " + c.getId() + " Course name: " + c.getName() + " Instructor: " + c.getInstructor());
                        String studentDashboardInput = getStudentDashboardInput();
                        if ("register".equals(studentDashboardInput)) {
                            Integer courseId = getCourseRegistryInput();
                            if (!listOfCoursesIds.contains(courseId))
                                System.out.println(courseId + " is not a valid course ID");
                            else if (!studentService.registeredOrNot(studentEmail, courseId))
                                studentService.registerStudentToCourse(studentEmail, courseId);
                            else System.out.println(name + " is already registered for that course");
                        }
                        else if ("logout".equals(studentDashboardInput)) loggedIn = false;
                        else System.out.println("Invalid input");
                    }
                    System.out.println("You are logged out");
                } else System.out.println("Invalid login");
            }
            else if ("logout".equals(smsDashboardInput)) System.out.println("You are not logged in");
            else System.out.println("Invalid input");
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


    private Integer getCourseRegistryInput() {
        CourseDao courseService = new CourseService(DatabaseConnection.MANAGEMENT_SYSTEM);
        List<CourseInterface> courses = ((CourseService) courseService).getAllCourses();
        StringBuilder prompt = new StringBuilder();
        prompt.append("Welcome to the Course Registration Dashboard!")
                .append("\nFrom here, enter the ID of any of the following options:");
        for (CourseInterface c : courses) prompt.append("\n" + c.getId() + " - " + c.getName() + " Instructor: " + c.getInstructor());
        return console.getIntegerInput(prompt.toString());
    }
}
