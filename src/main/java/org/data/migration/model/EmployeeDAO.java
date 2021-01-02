package org.data.migration.model;

import org.data.migration.controller.TxtWriter;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class EmployeeDAO {

    private Connection connection;
    private Properties properties = new Properties();

    private void createProperties() {
        try {
            properties.load(new FileReader("src/main/resources/login.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectionDAO(String url) {
        createProperties();
        String userName = properties.getProperty("userName");
        String password = properties.getProperty("password");;
        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDB(String query) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Request complete");
        } catch (SQLException e) {
            System.out.println("Could not process request.");;
        }
    }

    public ResultSet queryDB(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public void insertEmployees(List<EmployeeDTO> list) {
        PreparedStatement preparedStatement;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO `company`.`employees` (`emp_ID`, `name_preFix`, `first_name`, `middle_initial`, `last_name`," +
                            "`gender`, `email`, `date_of_birth`, `date_of_joining`, `salary`)" +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for(EmployeeDTO employee: list) {
                preparedStatement.setInt(1, Integer.parseInt(employee.getEmp_ID())); // first column
                preparedStatement.setString(2, employee.getNamePreFix()); // second column
                preparedStatement.setString(3, employee.getFirstName());
                preparedStatement.setString(4, employee.getMiddleInitial());
                preparedStatement.setString(5, employee.getLastName());
                preparedStatement.setString(6, employee.getGender());
                preparedStatement.setString(7, employee.getEmail());
                Date dateOfBirth = Date.valueOf(employee.getDob());
                preparedStatement.setDate(8, dateOfBirth);
                Date doj = Date.valueOf(employee.getDateOfJoining());
                preparedStatement.setDate(9, doj);
                preparedStatement.setInt(10, employee.getSalary());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertEmployee(int emp_ID, String namePreFix, String firstName, String middleInitial,
                                  String lastName, String gender, String email, LocalDate dob,
                                  LocalDate dateOfJoining, int salary) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO `employees`.`company` (`emp_ID`, `name_preFix`, `first_name`, `middle_initial`, `last_name`," +
                            "`gender`, `email`, `date_of_birth`, `date_of_joining`, `salary`)" +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1,emp_ID);
            preparedStatement.setString(2, namePreFix);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, middleInitial);
            preparedStatement.setString(5, lastName);
            preparedStatement.setString(6, gender);
            preparedStatement.setString(7, email);
            Date dateOfBirth = Date.valueOf(dob);
            preparedStatement.setDate(8, dateOfBirth);
            Date doj = Date.valueOf(dateOfJoining);
            preparedStatement.setDate(9, doj);
            preparedStatement.setInt(10, salary);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
