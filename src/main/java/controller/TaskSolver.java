package controller;

import model.MonitoredData;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

public class TaskSolver implements Tasks {

    private ArrayList<MonitoredData> activityLog;

    public TaskSolver() {
        this.activityLog = new ArrayList<>();
    }

    @Override
    public void task_1() throws IOException {

        String fileName = "/Activities.txt";

        String outputFile = "Task_1.txt";
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile, true);
        PrintStream outFile = new PrintStream(fileOutputStream);

        try (InputStream fis = this.getClass().getResourceAsStream(fileName);
             InputStreamReader isr = new InputStreamReader(fis,
                     StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            br.lines().forEach(line -> {
                MonitoredData data = new MonitoredData();
                data = data.convertString(line);
                activityLog.add(data);
                outFile.println(data.toString());
            });
        }
        outFile.close();
    }

    @Override
    public void task_2() throws FileNotFoundException {
        int count = (int) activityLog.stream().filter(distinctByKey(a -> a.getStart_time().getDayOfYear())).count();
        String outputFile = "Task_2.txt";
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile, true);
        PrintStream outFile = new PrintStream(fileOutputStream);
        outFile.println("Task2");
        outFile.println("Distinct days: "+count);
    }

    @Override
    public Map<String,  Integer>  task_3() {
        Map<String, Integer> countActivityFrequency = activityLog.stream().
                collect(groupingBy(MonitoredData::getActivityName, summingInt(d-> 1)));

        return countActivityFrequency;
    }

    @Override
    public  Map<Integer, Map<String, Integer>> task_4() {
        int firstDay = activityLog.get(0).getStart_time().getDayOfYear();

        Map<Integer, Map<String, Integer>> countActivityPerDayFrequency = activityLog.stream().
                collect(groupingBy(a->a.getDay(firstDay),
                        groupingBy(MonitoredData::getActivityName, summingInt(d-> 1))));

        return countActivityPerDayFrequency;
    }

    @Override
    public Map<String, Integer> task_5() {
        Map<String, Integer> countActivityFrequency = activityLog.stream().
                collect(groupingBy(MonitoredData::getActivityName, summingInt(MonitoredData::activityDuration)));

        return countActivityFrequency;
    }

    @Override
    public  List<String> task_6() {

        Map<String, Integer> countActivityFrequency = task_3();
        Map<String, Integer> countActivityLess5Mins = activityLog.stream().filter(a->a.activityDuration()<5*30)
                .collect(groupingBy(MonitoredData::getActivityName, summingInt(d-> 1)));

        List<String> finalActivityList = activityLog.stream().filter(a->{
            if (countActivityLess5Mins.get(a.getActivityName())!=null){
                if(countActivityLess5Mins.get(a.getActivityName())>=0.9*countActivityFrequency.get(a.getActivityName())){
                    return true;
                }
            }
            return false;
        }).map(MonitoredData::getActivityName).distinct().collect(Collectors.toList());

        return finalActivityList;
    }

    public static String convertToTime(int timeDifference){
        Duration d = Duration.parse("PT"+timeDifference+"S");
        int seconds = d.toSecondsPart();
        int minutes = d.toMinutesPart();
        int hours = d.toHoursPart();
        long days = d.toDaysPart();
        String h = (hours<=9) ?  "0"+hours : String.valueOf(hours);
        String m = (minutes<=9) ?  "0"+minutes : String.valueOf(minutes);
        String s = (seconds<=9) ?  "0"+seconds : String.valueOf(seconds);
        String string = h +":"+ m +":"+ s;

        string = (days>0) ? days + " days " + string : string ;

        return string;
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
