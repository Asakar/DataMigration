package org.data.migration.controller;
import org.data.migration.model.EmployeeDTO;

import java.util.List;

public class ThreadProgram {

    public void threads(List<EmployeeDTO> employeeDTO, int threads) {

        Thread[] threadList = new Thread[threads];
        int sizeOfListForEachThread = employeeDTO.size()/threads;
        int initialSize = sizeOfListForEachThread;
        int position = 0;
        long start = System.nanoTime();

        for(Thread t: threadList) {
            if(position == (initialSize*(threads-1))) {
                List<EmployeeDTO> employeeThread = employeeDTO.subList(position, employeeDTO.size());
                t = createThread(employeeThread);
                t.start();
                printTimeTaken(t, start);
                break;
            }
            List<EmployeeDTO> employeeThread = employeeDTO.subList(position, sizeOfListForEachThread);
            t = createThread(employeeThread);
            t.start();
            position+= initialSize;
            sizeOfListForEachThread += initialSize;
        }
    }

    private void printTimeTaken(Thread t, Long start) {
        while(t.isAlive()) {
        }
        long finish = System.nanoTime();
        long totalTime = (finish - start);
        System.out.println("Time taken to insert all employees into database: " +
                totalTime/1_000_000_000 + " seconds");
    }

    public Thread createThread(List<EmployeeDTO> employeeDTO) {
        return new Thread(new JDBCTask(employeeDTO));
    }

}
