package com.github.perscholas.service;

import com.github.perscholas.DatabaseConnection;
import com.github.perscholas.dao.StudentDao;
import com.github.perscholas.model.CourseInterface;
import com.github.perscholas.model.Student;
import com.github.perscholas.model.StudentInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// TODO - Implement respective DAO interface
public class StudentService implements StudentDao {

    private final DatabaseConnection dbc;

    public StudentService(DatabaseConnection dbc) {
        this.dbc = dbc;
    }

    public StudentService() {
        this(DatabaseConnection.MYSQL);
    }

    @Override
    public List<StudentInterface> getAllStudents() {
        ResultSet result = dbc.executeQuery("SELECT * FROM students");
        List<StudentInterface> list = new ArrayList<>();
        try {
            while (result.next()) {
                String studentEmail = result.getString("email");
                String name = result.getString("name");
                String password = result.getString("password");
                StudentInterface student = new Student(studentEmail, name, password);
                list.add(student);
            }
        } catch(SQLException se) {
            throw new Error(se);
        }

        return list;
    }

    @Override
    public StudentInterface getStudentByEmail(String studentEmail) {
        StudentInterface student = new Student();
        ResultSet result = dbc.executeQuery("SELECT * FROM students WHERE email = " + studentEmail);
        try {
            while (result.next()) {
                student.setEmail(result.getString("email"));
                student.setName(result.getString("name"));
                student.setPassword(result.getString("password"));
            }
        } catch(SQLException se) {
            throw new Error(se);
        }
        return student;
    }

    @Override
    public Boolean validateStudent(String studentEmail, String password) {
        ResultSet result = dbc.executeQuery("SELECT * FROM students WHERE email = " + studentEmail);
        try {
            while (result.next()) {
                if (result.getString("password").equals(password)) return true;
            }
        } catch(SQLException se) {
            throw new Error(se);
        }
        return false;
    }

    @Override
    public void registerStudentToCourse(String studentEmail, int courseId) {

    }

    @Override
    public List<CourseInterface> getStudentCourses(String studentEmail) {
        return null;
    }
}
