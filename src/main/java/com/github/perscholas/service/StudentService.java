package com.github.perscholas.service;

import com.github.perscholas.DatabaseConnection;
import com.github.perscholas.dao.StudentDao;
import com.github.perscholas.model.CourseInterface;
import com.github.perscholas.model.Student;
import com.github.perscholas.model.StudentInterface;

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
    public List<StudentInterface> getAllStudents() throws SQLException {
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
    public StudentInterface getStudentByEmail(String studentEmail) throws SQLException {
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
    public Boolean validateStudent(String studentEmail, String password) throws SQLException {
        StudentInterface studentLogin = getStudentByEmail(studentEmail);

        return studentLogin.getPassword().equals(password);
    }

    @Override
    public void registerStudentToCourse(String studentEmail, int courseId) {
        dbc.executeStatement("insert into Studentregistration(email, id) values('"
                + studentEmail + "', '"
                + courseId + "'");
    }

    @Override
    public List<CourseInterface> getStudentCourses(String studentEmail) {
        return null;
    }
}
