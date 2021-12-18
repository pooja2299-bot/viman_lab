package com.company;

import java.util.*;

public class Main {

    private static final Integer binCapacity = 30;
    private static final Integer binRange = 50;
    private static final Integer binMla = 4;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Please Enter Number of Tasks");

        int numOfTasks = sc.nextInt();
        List<Integer> tasksExecutionTimeList = new ArrayList<>();

        for (int i = 0; i < numOfTasks; i++) {
            tasksExecutionTimeList.add(new Random().nextInt(binRange - 1) + 1);
        }

        System.out.println(tasksExecutionTimeList);

        System.out.println("Please enter comma separated conflict rations between tasks numbering between 1 to " + numOfTasks);

        List<String> conflictList = new ArrayList<>();

        while (sc.hasNextLine()) {
            String val = sc.nextLine();
            if (val.equals("break")) break;
            conflictList.add(val);
        }

        conflictList.remove(0);

        System.out.println(conflictList);


        Map<Integer, HashSet<Integer>> conflictMap = new TreeMap<>();

        for (String conflictString : conflictList) {
            String[] conflictLine = conflictString.split(",");
            putValuesInConflictMap(conflictMap, conflictLine[0], conflictLine[1]);
            putValuesInConflictMap(conflictMap, conflictLine[1], conflictLine[0]);
        }
        System.out.println("ConflictMap created is " + conflictMap);


        int finalExecutionTime = getFinalExecutionTime(tasksExecutionTimeList);
        
        System.out.println("Bin MLA value is " + binMla);

        System.out.println("End program and execution time is " + finalExecutionTime);

        sc.close();
    }

    private static int getFinalExecutionTime(List<Integer> tasksExecutionTimeList) {
        int binLimit = Main.binMla;
        boolean valueAdded = false;

        int finalExecutionTime = 0;

        List<Integer> maxTimeList = new ArrayList<>();

        while (tasksExecutionTimeList.size() != 0) {
            Collections.sort(tasksExecutionTimeList, Collections.reverseOrder());
            List<Integer> dummyTasksExecutionTimeList = new ArrayList<>(tasksExecutionTimeList);
            tasksExecutionTimeList.clear();

            int valuateAdded;

            for (Integer integer : dummyTasksExecutionTimeList) {
                if (integer <= binCapacity) {

                    valuateAdded = integer;

                } else {

                    valuateAdded = binCapacity;
                    tasksExecutionTimeList.add(integer - binCapacity);

                }

                maxTimeList.add(valuateAdded);

                if (binLimit == 1) {

                    finalExecutionTime += Collections.max(maxTimeList);
                    binLimit = Main.binMla;
                    maxTimeList.clear();
                    valueAdded = true;

                } else {

                    binLimit--;

                }
            }

        }

        if (!valueAdded) {
            finalExecutionTime += Collections.max(maxTimeList);
        }
        return finalExecutionTime;
    }


    private static void putValuesInConflictMap(Map<Integer, HashSet<Integer>> conflictMap, String key, String value) {
        if (conflictMap.containsKey(Integer.valueOf(key))) {
            HashSet<Integer> integers = conflictMap.get(Integer.valueOf(key));
            integers.add(Integer.valueOf(value));

        } else {
            HashSet<Integer> objectHashSet = new HashSet<>();
            objectHashSet.add(Integer.valueOf(value));
            conflictMap.put(Integer.parseInt(key), objectHashSet);
        }
    }
}
