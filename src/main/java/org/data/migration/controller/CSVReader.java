package org.data.migration.controller;

import org.data.migration.model.EmployeeDTO;

import java.io.*;
import java.util.*;

public class CSVReader {

    private static List<EmployeeDTO> employees = new ArrayList<>();
    private static List<EmployeeDTO> tempEmployees = new ArrayList<>();
    private static Set<String> employeeIds = new HashSet<>();
    private static Set<String> employeeEmails = new HashSet<>();
    private static Set<String> corruptEmployeeIds = new HashSet<>();
    private static Set<String> corruptEmployeeEmails = new HashSet<>();
    private static int linesRead;

    public static List<EmployeeDTO> readAndCleanEmployees(String path) {

        linesRead = 0;
        long start = System.nanoTime();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            bufferedReader.readLine();

            while((line = bufferedReader.readLine()) != null) {
                String[] array = line.split(",");
                EmployeeDTO person = createEmployee(array);
                tempEmployees.add(person);
                String id = person.getEmp_ID();
                String email = person.getEmail();
                if(!employeeEmails.contains(email) && !employeeIds.contains(id)) {
                    employeeEmails.add(email);
                    employeeIds.add(id);
                } else {
                    corruptEmployeeEmails.add(email);
                    corruptEmployeeIds.add(id);
                }
                linesRead++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Number of lines read: " + linesRead);
        long finish = System.nanoTime();
        long totalTime = (finish - start);
        System.out.println("Time taken to read CSV: " + totalTime/1_000_000 + " milliseconds");

        createCorruptEmployeesListBasedOnId();
        createCorruptEmployeesListBasedOnEmail();

        employeeIds.removeAll(corruptEmployeeIds);
        employeeEmails.removeAll(corruptEmployeeEmails);

        removeDuplicateEmployees();
        return employees;
    }

    public static List<EmployeeDTO> getTempEmployees() {
        return tempEmployees;
    }

    public static int getLinesRead() {
        return linesRead;
    }

    public static void createCorruptEmployeesListBasedOnEmail() {

        List<EmployeeDTO> corruptEmployees = new ArrayList<>();

        for(EmployeeDTO employeeCheck: getTempEmployees()) {
            String email = employeeCheck.getEmail();
            if(employeeEmails.contains(email) && corruptEmployeeEmails.contains(email)) {
                corruptEmployees.add(employeeCheck);
            }
        }
        if(corruptEmployees.size() > 0) {
            TxtWriter.writeEmployees(corruptEmployees, "employees_with_corrupt_emails");
        }

    }

    public static void removeDuplicateEmployees() {

        for(EmployeeDTO employeeCheck: getTempEmployees()) {
            String id = employeeCheck.getEmp_ID();
            String email = employeeCheck.getEmail();
            if(employeeIds.contains(id) && employeeEmails.contains(email)) {
                employees.add(employeeCheck);
            }
        }

    }

    public static void createCorruptEmployeesListBasedOnId() {

        List<EmployeeDTO> corruptEmployees = new ArrayList<>();

        for(EmployeeDTO employeeCheck: getTempEmployees()) {
            String id = employeeCheck.getEmp_ID();
            if(employeeIds.contains(id) && corruptEmployeeIds.contains(id)) {
                corruptEmployees.add(employeeCheck);
            }
        }

        if(corruptEmployees.size() > 0) {
            TxtWriter.writeEmployees(corruptEmployees, "employees_with_corrupt_Ids");
        }

    }

    public static EmployeeDTO createEmployee(String[] array) {

        EmployeeDTO employee = new EmployeeDTO();
        employee.setEmp_ID(array[0]);
        employee.setNamePreFix(array[1]);
        employee.setFirstName(array[2]);
        employee.setMiddleInitial(array[3]);
        employee.setLastName(array[4]);
        employee.setGender(array[5]);
        employee.setEmail(array[6]);
        employee.setDob(array[7]);
        employee.setDateOfJoining(array[8]);
        employee.setSalary(array[9]);
        return employee;

    }

}
