package com.github.perscholas.service;

import com.github.perscholas.DatabaseConnection;
import com.github.perscholas.dao.StudentDao;
import com.github.perscholas.model.*;

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
        this(DatabaseConnection.MARIADB);
    }


    @Override
    public List<StudentInterface> getAllStudents() {
        ResultSet result = dbc.executeQuery("SELECT * FROM `student`");
        List<StudentInterface> list = new ArrayList<>();
        try {
            while (result.next()) {
                String studentEmail = result.getString("email");
                String name = result.getString("name");
                String password = result.getString("password");
                StudentInterface student = new Student(studentEmail, name, password);
            }
        } catch (SQLException se) {
            throw new Error(se);
        }
        return list;
    }

    @Override
    public StudentInterface getStudentByEmail(String studentEmail) {
        ResultSet result = dbc.executeQuery("SELECT * FROM `student` WHERE `email` = '"+ studentEmail +"'");
        List<StudentService> list = new ArrayList<>();
        try {
            while (result.next()) {
                studentEmail = result.getString("email");
                String name = result.getString("name");
                String password = result.getString("password");
                StudentInterface student = new Student(studentEmail, name, password);
                return student;
            }
        } catch (SQLException se) {
            throw new Error(se);
        }
        return null;
    }

    @Override
    public Boolean validateStudent(String studentEmail, String password) {
        StudentInterface studentLogin = getStudentByEmail(studentEmail);

        return studentLogin.getPassword().equals(password);
    }

    @Override
    public void registerStudentToCourse(String studentEmail, int courseId) {
        String sqlStatement = "INSERT INTO studentregistration VALUES('"
                + studentEmail + "','"
                + courseId + "')";
        dbc.executeStatement(sqlStatement);
    }

    @Override
    public List<CourseInterface> getStudentCourses(String studentEmail) {
        ResultSet result = dbc.executeQuery("SELECT c.id, name, instructor " +
                "FROM course c, studentregistration sr " +
                "WHERE c.id = sr.id " +
                "AND sr.email = '" + studentEmail + "'");
        List<CourseInterface> list = new ArrayList<>();
        try {
            while (result.next()) {
                Integer id = result.getInt("id");
                String name = result.getString("name");
                String instructor = result.getString("instructor");
                CourseInterface course = new Course(id, name, instructor);
                list.add(course);
                return list;
            }
        } catch (SQLException se) {
            throw new Error(se);
        }
        return null;
    }
}
