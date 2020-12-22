package org.data.migration;

import org.data.migration.model.EmployeeDAO;
import org.data.migration.model.EmployeeDTO;
import org.data.migration.controller.CSVReader;
import org.data.migration.controller.ThreadProgram;

import java.util.List;

public class App {

    public static void main(String[] args) {
        long start = System.nanoTime();
        List<EmployeeDTO> cleanList = CSVReader.readAndCleanEmployees(
                "src/main/resources/EmployeeRecords.csv");
        ThreadProgram test = new ThreadProgram();
        test.threads(cleanList, 27);
        long finish = System.nanoTime();
        long totalTime = (finish - start);
        System.out.println("Overall time taken: " + totalTime/1_000_000_000 + " seconds");
    }

}
