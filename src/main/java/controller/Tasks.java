package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Tasks {
    void task_1() throws IOException;
    void task_2() throws FileNotFoundException;
    Map<String,  Integer> task_3();
    Map<Integer, Map<String, Integer>> task_4();
    Map<String, Integer> task_5();
    List<String> task_6();
}
