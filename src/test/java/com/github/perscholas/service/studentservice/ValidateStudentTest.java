package com.github.perscholas.service.studentservice;

import com.github.perscholas.JdbcConfigurator;
import com.github.perscholas.service.StudentService;
import org.junit.*;

import static org.junit.Assert.assertTrue;

/**
 * @author leonhunter
 * @created 02/12/2020 - 8:24 PM
 */ // TODO - Define tests
public class ValidateStudentTest {
    @Before
    public void setup(){
        JdbcConfigurator.initialize();
    }

    @Test
    public void test(){
        StudentService studentService = new StudentService();
        Boolean isValidLogin = studentService.validateStudent("lou", "uol");
        assertTrue(isValidLogin);
    }
}
