package org.data.migration;

import org.data.migration.controller.CSVReader;
import org.data.migration.controller.TxtWriter;
import org.data.migration.model.EmployeeDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class TxtWriterTest {

    @Test
    public void areEmployeesWrittenOutToCSVFileCorrectly() {
        List<EmployeeDTO> temp = new LinkedList<>();
        EmployeeDTO employee = new EmployeeDTO();
        employee.setEmp_ID("87384");
        employee.setNamePreFix("Mr.");
        employee.setFirstName("Po");
        employee.setMiddleInitial("I");
        employee.setLastName("go");
        employee.setGender("Male");
        employee.setEmail("p@gmail.com");
        employee.setDob("09/09/1990");
        employee.setDateOfJoining("03/02/2020");
        employee.setSalary("30000");
        temp.add(employee);
        TxtWriter.writeEmployees(temp, "testFile");
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader("testFile.csv"));
            String line;
            line = bufferedReader.readLine();
            Assertions.assertEquals(
                    "Emp ID,Name Prefix,First Name,Middle Initial,Last Name,Gender,E Mail,Date of Birth,Date of Joining,Salary", line);
            line = bufferedReader.readLine();
            Assertions.assertEquals(
                    "87384,Mr.,Po,I,go,Male,p@gmail.com,1990-09-09,2020-03-02,30000", line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
