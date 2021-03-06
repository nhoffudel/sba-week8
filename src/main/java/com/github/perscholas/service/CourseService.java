package com.github.perscholas.service;

import com.github.perscholas.DatabaseConnection;
import com.github.perscholas.dao.CourseDao;
import com.github.perscholas.model.Course;
import com.github.perscholas.model.CourseInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// TODO - Implement respective DAO interface
public class CourseService implements CourseDao {

    private final DatabaseConnection dbc;

    public CourseService(DatabaseConnection dbc) {
        this.dbc = dbc;
    }

    public CourseService() {
        this(DatabaseConnection.MANAGEMENT_SYSTEM);
    }

    @Override
    public List<CourseInterface> getAllCourses() {
        ResultSet result = dbc.executeQuery("SELECT * FROM course");
        List<CourseInterface> list = new ArrayList<>();
        try {
            while (result.next()) {
                Integer id = result.getInt("id");
                String name = result.getString("name");
                String instructor = result.getString("instructor");
                CourseInterface student = new Course(id, name, instructor);
                list.add(student);
            }
        } catch(SQLException se) {
            throw new Error(se);
        }

        return list;
    }

    public List<Integer> getAllCourseIDs() {
        ResultSet result = dbc.executeQuery("SELECT * FROM course");
        List<Integer> list = new ArrayList<>();
        try {
            while (result.next()) {
                list.add(result.getInt("id"));
            }
        } catch(SQLException se) {
            throw new Error(se);
        }

        return list;
    }
}
