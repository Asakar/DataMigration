package org.data.migration.controller;

import org.data.migration.model.EmployeeDAO;
import org.data.migration.model.EmployeeDTO;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class JDBCTask implements Runnable{

    private List<EmployeeDTO> employeeDTO;
    private Properties properties;

    public JDBCTask(List<EmployeeDTO> employeeDTO) {
        this.employeeDTO = employeeDTO;
    }

    @Override
    public void run() {

        EmployeeDAO employeeDAO = new EmployeeDAO();
        employeeDAO.connectionDAO("jdbc:mysql://localhost:3306/employees");
        employeeDAO.insertEmployees(employeeDTO);

    }

}
