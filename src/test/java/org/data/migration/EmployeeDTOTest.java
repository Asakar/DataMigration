package org.data.migration;

import org.data.migration.model.EmployeeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

public class EmployeeDTOTest {

    EmployeeDTO employeeDTO = new EmployeeDTO();

    @Test
    public void isEmployeeIDSetCorrectly() {
        employeeDTO.setEmp_ID("9092");
        Assertions.assertEquals("9092", employeeDTO.getEmp_ID());
    }

    @Test
    public void isEmployeeNameSetCorrectly() {
        employeeDTO.setNamePreFix("Mr.");
        employeeDTO.setFirstName("Po");
        employeeDTO.setMiddleInitial("I");
        employeeDTO.setLastName("King");
        Assertions.assertEquals("Mr.", employeeDTO.getNamePreFix());
        Assertions.assertEquals("Po", employeeDTO.getFirstName());
        Assertions.assertEquals("I", employeeDTO.getMiddleInitial());
        Assertions.assertEquals("King", employeeDTO.getLastName());
    }

    @Test
    public void isEmployeeGenderSetCorrectly() {
        employeeDTO.setGender("Male");
        Assertions.assertEquals("Male", employeeDTO.getGender());
    }

    @Test
    public void isEmployeeEmailSetCorrectly() {
        employeeDTO.setEmail("hfdh@gmail.com");
        Assertions.assertEquals("hfdh@gmail.com", employeeDTO.getEmail());
    }

    @Test
    public void isEmployeeDobSetCorrectly() {
        employeeDTO.setDob("02/04/1990");
        LocalDate localDate = LocalDate.of(1990,2,4);
        Assertions.assertEquals(localDate, employeeDTO.getDob());
    }

    @Test
    public void isEmployeeDateOfJoiningSetCorrectly() {
        employeeDTO.setDateOfJoining("01/01/2020");
        LocalDate localDate = LocalDate.of(2020,1,1);
        Assertions.assertEquals(localDate, employeeDTO.getDateOfJoining());
    }

    @Test
    public void isEmployeeSalarySetCorrectly() {
        employeeDTO.setSalary("30000");
        Integer actual = 30000;
        Assertions.assertEquals(actual, employeeDTO.getSalary());
    }

    @Test
    public void isEmployeeToStringCorrect() {
        EmployeeDTO temp = new EmployeeDTO();
        temp.setEmp_ID("87384");
        temp.setNamePreFix("Mr.");
        temp.setFirstName("Po");
        temp.setMiddleInitial("I");
        temp.setLastName("go");
        temp.setGender("Male");
        temp.setEmail("p@gmail.com");
        temp.setDob("09/09/1990");
        temp.setDateOfJoining("03/02/2020");
        temp.setSalary("30000");
        String result = "87384,Mr.,Po,I,go,Male,p@gmail.com,1990-09-09,2020-03-02,30000";
        Assertions.assertEquals(result, temp.toString());
    }


}
