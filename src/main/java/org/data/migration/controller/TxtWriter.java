package org.data.migration.controller;

import org.data.migration.model.EmployeeDTO;

import java.io.*;
import java.util.List;

public class TxtWriter {

    public static void writeEmployees(List<EmployeeDTO> employees, String name) {
        BufferedWriter bufferedWriter;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(name + ".csv"));
            bufferedWriter.write("Emp ID,Name Prefix,First Name,Middle Initial,Last Name,Gender,E Mail,Date of Birth,Date of Joining,Salary");
            bufferedWriter.newLine();
            for(EmployeeDTO employee: employees) {
                bufferedWriter.write(employee.toString());
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
