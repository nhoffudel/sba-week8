package com.github.perscholas.service;

import com.github.perscholas.DatabaseConnection;
import com.github.perscholas.dao.CourseDao;
import com.github.perscholas.dao.StudentDao;
import com.github.perscholas.model.CourseInterface;
import com.github.perscholas.model.Student;
import com.github.perscholas.model.StudentInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO - Implement respective DAO interface
public class StudentService implements StudentDao {

    private final DatabaseConnection dbc;

    public StudentService(DatabaseConnection dbc) {
        this.dbc = dbc;
    }

    public StudentService() {
        this(DatabaseConnection.MANAGEMENT_SYSTEM);
    }

    @Override
    public List<StudentInterface> getAllStudents() {
        ResultSet result = dbc.executeQuery("SELECT * FROM student");
        List<StudentInterface> list = new ArrayList<>();
        try {
            while (result.next()) {
                String studentEmail = result.getString("email");
                String name = result.getString("name");
                String password = result.getString("password");
                StudentInterface student = new Student(studentEmail, name, password);
                list.add(student);
            }
        } catch (SQLException se) {
            throw new Error(se);
        }

        return list;
    }

    @Override
    public StudentInterface getStudentByEmail(String studentEmail) {
        StudentInterface student = new Student();
        ResultSet result = dbc.executeQuery("SELECT * FROM student;");
        try {
            while (result.next()) {
                if (studentEmail.equals(result.getString("email"))) {
                    student.setEmail(result.getString("email"));
                    student.setName(result.getString("name"));
                    student.setPassword(result.getString("password"));
                }
            }
        } catch (SQLException se) {
            throw new Error(se);
        }
        return student;
    }

    @Override
//    public Boolean validateStudent(String studentEmail, String password) {
//        ResultSet result = dbc.executeQuery("SELECT * FROM Student WHERE 'email' = '" + studentEmail + "';");
//        try {
//            while (result.next()) {
//                System.out.println(result.getString("email"));
//                if (result.getString("password").equals(password)){
//                    System.out.println("valid password");
//                    return true;
//                }
//            }
//        } catch(SQLException se) {
//            throw new Error(se);
//        }
//        System.out.println("wrong password");
//        return false;
//    }

    public Boolean validateStudent(String studentEmail, String password) {
        ResultSet result = dbc.executeQuery("SELECT * FROM student");
        try {
            while (result.next()) {
                String email = result.getString("email");
                String pass = result.getString("password");
                if (studentEmail.equals(email) && password.equals(pass)) return true;
            }
        } catch (SQLException se) {
            throw new Error(se);
        }
        return false;
    }

    public Boolean registeredOrNot(String studentEmail, Integer id) {
        ResultSet result = dbc.executeQuery("SELECT * FROM student_course");
        try {
            while (result.next()) {
                String email = result.getString("studentemail");
                Integer courseid = result.getInt("courseid");
                if (studentEmail.equals(email) && courseid.equals(id)) return true;
            }
        } catch (SQLException se) {
            throw new Error(se);
        }
        return false;
    }

    @Override
    public void registerStudentToCourse(String studentEmail, int courseId) {
        dbc.executeUpdate("insert into student_course (studentemail, courseid) values ('" + studentEmail+"', '"+ courseId+"');");
        System.out.println("Successfully registered for course");
    }

    @Override
    public List<CourseInterface> getStudentCourses(String studentEmail) {
        CourseDao courseService = new CourseService(DatabaseConnection.MANAGEMENT_SYSTEM);
        List<CourseInterface> listOfCourses = ((CourseService) courseService).getAllCourses();
        Map courses = new HashMap();
        for (CourseInterface c : listOfCourses) {
            courses.put(c.getId(), c);
        }
        listOfCourses.clear();
        ResultSet result = dbc.executeQuery("SELECT * FROM student_course");
        try {
            while (result.next()) {
                if (result.getString("studentemail").equals(studentEmail)) {
                    listOfCourses.add((CourseInterface) courses.get(result.getInt("courseid")));
                }
            }
        } catch (SQLException se) {
            throw new Error(se);
        }
        return listOfCourses;
    }
}
