package org.data.migration.controller;

import org.data.migration.model.EmployeeDAO;
import org.data.migration.model.EmployeeDTO;

import java.util.List;

public class JDBCTask implements Runnable{

    private List<EmployeeDTO> employeeDTO;

    public JDBCTask(List<EmployeeDTO> employeeDTO) {
        this.employeeDTO = employeeDTO;
    }

    @Override
    public void run() {
        EmployeeDAO.connectionDAO("jdbc:mysql://localhost:3306/employees");
        EmployeeDAO.insertEmployees(employeeDTO);
    }

}
