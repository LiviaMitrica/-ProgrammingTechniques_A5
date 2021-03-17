package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

public class Launcher {
    public static void main(String[] args) throws IOException {
        TaskSolver taskSolver = new TaskSolver();
        taskSolver.task_1();
        taskSolver.task_2();

        Map<String,  Integer> task3 = taskSolver.task_3();
        String outputFile3 = "Task_3.txt";
        FileOutputStream fileOutputStream3 = new FileOutputStream(outputFile3, true);
        PrintStream outFile3 = new PrintStream(fileOutputStream3);
        outFile3.println("Task3");
        outFile3.println("Activity: "+ " Frequency over entire period");
        task3.forEach((value, key) -> {
            outFile3.println( value + " : " + key);
        });

        Map<Integer, Map<String, Integer>> task4 = taskSolver.task_4();
        String outputFile4 = "Task_4.txt";
        FileOutputStream fileOutputStream4 = new FileOutputStream(outputFile4, true);
        PrintStream outFile4 = new PrintStream(fileOutputStream4);
        outFile4.println("Task4");
        task4.forEach((value, key)->{
            outFile4.println( "Day "+ value + ":");
            key.forEach((v, k) -> {
                outFile4.println( v + " : " + k);
            });
            outFile4.println();
        });

        Map<String, Integer> task5 = taskSolver.task_5();
        String outputFile5 = "Task_5.txt";
        FileOutputStream fileOutputStream5 = new FileOutputStream(outputFile5, true);
        PrintStream outFile5 = new PrintStream(fileOutputStream5);
        outFile5.println("Task5");
        outFile5.println("Activity : Duration over entire period");
        task5.forEach((value, key) -> {
            outFile5.println( value + " : "+ TaskSolver.convertToTime(key));
        });

        List<String> task6 = taskSolver.task_6();
        String outputFile6 = "Task_6.txt";
        FileOutputStream fileOutputStream6 = new FileOutputStream(outputFile6, true);
        PrintStream outFile6 = new PrintStream(fileOutputStream6);
        outFile6.println("Task6");
        outFile6.println("Activities having more than 90% of the monitoring records with duration less than 5 minutes:");
        task6.stream().forEach( e->{
            outFile6.println(e);
        });
    }
}
