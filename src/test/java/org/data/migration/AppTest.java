package org.data.migration;

import org.data.migration.controller.CSVReader;
import org.data.migration.controller.ThreadProgram;
import org.data.migration.model.EmployeeDAO;
import org.data.migration.model.EmployeeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AppTest {

    int recordsInTestFile = 5;
    int numberOfCorruptRecordsInFile = 4;
    private static List<EmployeeDTO> fileList;
    String databaseConnectionURL = "jdbc:mysql://localhost:3306/employees";

    @BeforeAll
    static void readFile() {
        fileList = CSVReader.readAndCleanEmployees("src/main/resources/test.csv");
    }

    @Test
    public void isFileCompletelyRead() {
        Assertions.assertEquals(recordsInTestFile, CSVReader.getLinesRead());
    }

    @Test
    public void areAllCleanEmployeesExtractedCorrectly() {
        Assertions.assertEquals(recordsInTestFile-numberOfCorruptRecordsInFile, fileList.size());
        Assertions.assertEquals(
                "111,Mrs.,Panda,A,Po,F,p@exxonmobil.com,2000-01-01,2020-01-01,30000", fileList.get(0).toString());
    }

    @Test
    public void areAllEmployeesWithCorruptIdsExtracted() {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader("employees_with_corrupt_Ids.csv"));
            String line = bufferedReader.readLine();
            Assertions.assertEquals("Emp ID,Name Prefix,First Name,Middle Initial,Last Name,Gender,E Mail,Date of Birth,Date of Joining,Salary", line);
            line = bufferedReader.readLine();
            Assertions.assertEquals("333,Mr.,Kitten,C,Kai,M,k@hotmail.com,2000-02-02,2020-01-01,4000", line);
            line = bufferedReader.readLine();
            Assertions.assertEquals("333,Mr.,Kit,C,K,M,j@hotmail.com,2000-02-02,2020-01-01,4000", line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void areAllEmployeesWithCorruptEmailsExtracted() {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader("employees_with_corrupt_emails.csv"));
            String line = bufferedReader.readLine();
            Assertions.assertEquals("Emp ID,Name Prefix,First Name,Middle Initial,Last Name,Gender,E Mail,Date of Birth,Date of Joining,Salary", line);
            line = bufferedReader.readLine();
            Assertions.assertEquals("222,Mrs.,Lion,B,Leo,F,l@yahoo.co.uk,2000-02-02,2020-01-01,100", line);
            line = bufferedReader.readLine();
            Assertions.assertEquals("444,Mr.,jo,C,li,M,l@yahoo.co.uk,2000-02-02,2020-01-01,4000", line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void areAllGoodRecordsAddedCorrectlyToDatabase() {

        EmployeeDAO employeeDAO = new EmployeeDAO();
        employeeDAO.connectionDAO(databaseConnectionURL);
        employeeDAO.updateDB("TRUNCATE `company`.`employees`");
        ThreadProgram test = new ThreadProgram();
        test.threads(fileList, 32);
        ResultSet databaseResults =  employeeDAO.queryDB("SELECT * FROM company.employees");
        int i = 0;
        assert databaseResults != null;
        try {
            while (databaseResults.next()) {
                databaseResults.getInt(1);
                Assertions.assertEquals(Integer.parseInt(fileList.get(i).getEmp_ID()), databaseResults.getInt(1));
                Assertions.assertEquals(fileList.get(i).getNamePreFix(), databaseResults.getString(2));
                Assertions.assertEquals(fileList.get(i).getFirstName(), databaseResults.getString(3));
                Assertions.assertEquals(fileList.get(i).getMiddleInitial(), databaseResults.getString(4));
                Assertions.assertEquals(fileList.get(i).getLastName(), databaseResults.getString(5));
                Assertions.assertEquals(fileList.get(i).getGender(), databaseResults.getString(6));
                Assertions.assertEquals(fileList.get(i).getEmail(), databaseResults.getString(7));
                Date dob = databaseResults.getDate(8);
                Assertions.assertEquals(fileList.get(i).getDob(), dob.toLocalDate());
                Date doj = databaseResults.getDate(9);
                Assertions.assertEquals(fileList.get(i).getDateOfJoining(), doj.toLocalDate());
                Assertions.assertEquals(fileList.get(i).getSalary(), databaseResults.getInt(10));
                i++;
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }

        Assertions.assertEquals(i, (recordsInTestFile - numberOfCorruptRecordsInFile));
    }

}
