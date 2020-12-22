package org.data.migration;

import org.data.migration.controller.CSVReader;
import org.data.migration.controller.ThreadProgram;
import org.data.migration.model.EmployeeDAO;
import org.data.migration.model.EmployeeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AppTest {

    private static String testFile = "src/main/resources/test.csv";
    private static int recordsInTestFile = 5;
    private static int numberOfCorruptRecordsInFile = 4;

    @Test
    public void isFileCompletelyRead() {
        long lineCount = 0;
        CSVReader.readAndCleanEmployees(testFile);
        Assertions.assertEquals(recordsInTestFile, CSVReader.getLinesRead());
    }

    /*
    This test will ensure that my Employee DTO is creating my employees needed to be added correctly.
    This will also test the thread programming to ensure they are also doing their job of creating a connection
    to the database and is inserting the employees correctly into the database using the Employee DAO class.
    This test will also test if the database is actually being populated. This will if my DAO is able to query
    from the data base correctly. As there are 4 corrupt employees in the test file it should only add one employee
    to the database which will be Panda Po. This also tests that the employees with duplicate emails and employee Id are
    not added to the database. Please refer to the csv files created for proof of this in which there should be two
    employees in each.
     */
    @Test
    public void isCleanRecordsAddedCorrectlyToDatabase() throws SQLException {

        EmployeeDAO employeeDAO = new EmployeeDAO();
        employeeDAO.connectionDAO("jdbc:mysql://localhost:3306/employees");
        employeeDAO.updateDB("TRUNCATE `employees`.`company`");
        List<EmployeeDTO> fileList = CSVReader.readAndCleanEmployees("src/main/resources/test.csv");
        ThreadProgram test = new ThreadProgram();
        test.threads(fileList, 32);
        ResultSet databaseResults =  employeeDAO.queryDB("SELECT * FROM employees.company");
        int recordNo = 0;
        assert databaseResults != null;

        while (databaseResults.next()) {
            databaseResults.getInt(1);
            Assertions.assertEquals(Integer.parseInt(fileList.get(recordNo).getEmp_ID()), databaseResults.getInt(1));
            Assertions.assertEquals(fileList.get(recordNo).getNamePreFix(), databaseResults.getString(2));
            Assertions.assertEquals(fileList.get(recordNo).getFirstName(), databaseResults.getString(3));
            Assertions.assertEquals(fileList.get(recordNo).getMiddleInitial(), databaseResults.getString(4));
            Assertions.assertEquals(fileList.get(recordNo).getLastName(), databaseResults.getString(5));
            Assertions.assertEquals(fileList.get(recordNo).getGender(), databaseResults.getString(6));
            Assertions.assertEquals(fileList.get(recordNo).getEmail(), databaseResults.getString(7));
            Date dob = databaseResults.getDate(8);
            Assertions.assertEquals(fileList.get(recordNo).getDob(), dob.toLocalDate());
            Date doj = databaseResults.getDate(9);
            Assertions.assertEquals(fileList.get(recordNo).getDateOfJoining(), doj.toLocalDate());
            Assertions.assertEquals(fileList.get(recordNo).getSalary(), databaseResults.getInt(10));
            recordNo++;
        }

        Assertions.assertEquals(recordNo, (recordsInTestFile - numberOfCorruptRecordsInFile));
    }

}
