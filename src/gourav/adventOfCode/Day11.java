package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Day11 {
    public static void main(String[] args) {
        System.out.println("Puzzle 1 Answer = " + puzzle1(getItems(), getTestRules()));
        System.out.println("Puzzle 2 Answer = " + puzzle2(getItems(), getTestRules()));
    }

    private static long puzzle1(List<Queue<Long>> items, List<List<Integer>> testRules) {
        final int n = items.size();
        final long[] inspectionCounts = getInspectionCounts(items, testRules, 1);
        Arrays.sort(inspectionCounts);
        return inspectionCounts[n - 2] * inspectionCounts[n - 1];
    }

    private static long puzzle2(List<Queue<Long>> items, List<List<Integer>> testRules) {
        final int n = items.size();
        final long[] inspectionCounts = getInspectionCounts(items, testRules, 2);
        Arrays.sort(inspectionCounts);
        return inspectionCounts[n - 2] * inspectionCounts[n - 1];
    }

    private static long[] getInspectionCounts(List<Queue<Long>> items, List<List<Integer>> testRules, int part) {
        final int n = items.size();
        final long[] inspectionCounts = new long[n];
        final int rounds = (part == 1) ? 20 : 10000;

        for (int round = 0; round < rounds; round++) {
            for (int i = 0; i < n; i++) {
                final Queue<Long> queue = items.get(i);
                while (queue.size() > 0) {
                    long worryLevel = getNewWorryLevel(i, queue.remove(), part);
                    inspectionCounts[i]++;

                    final List<Integer> testRule = testRules.get(i);
                    if (worryLevel % testRule.get(0) == 0) {
                        items.get(testRule.get(1)).add(worryLevel);
                    } else {
                        items.get(testRule.get(2)).add(worryLevel);
                    }
                }
            }
        }

        return inspectionCounts;
    }

    private static long getNewWorryLevel(int monkey, long oldLevel, int part) {
        final List<String> operation = getOperations().get(monkey);
        final long val = operation.get(1).equals("old") ? oldLevel : Long.parseLong(operation.get(1));

        long newLevel;
        if (operation.get(0).equals("*")) {
            newLevel = oldLevel * val;
        } else {
            newLevel = oldLevel + val;
        }

        return (part == 1) ? (newLevel / 3) : (newLevel % getProductOfTestRulesDivisors());
    }

    private static long getProductOfTestRulesDivisors() {
        final List<List<Integer>> testRules = getTestRules();
        long lcm = 1;
        for (List<Integer> testRule : testRules) {
            lcm *= testRule.get(0);
        }
        return lcm;
    }

    private static List<Queue<Long>> getItems() {
        List<Queue<Long>> items = new ArrayList<>();
        items.add(new PriorityQueue<>(Arrays.asList(75L, 75L, 98L, 97L, 79L, 97L, 64L)));
        items.add(new PriorityQueue<>(Arrays.asList(50L, 99L, 80L, 84L, 65L, 95L)));
        items.add(new PriorityQueue<>(Arrays.asList(96L, 74L, 68L, 96L, 56L, 71L, 75L, 53L)));
        items.add(new PriorityQueue<>(Arrays.asList(83L, 96L, 86L, 58L, 92L)));
        items.add(new PriorityQueue<>(Collections.singletonList(99L)));
        items.add(new PriorityQueue<>(Arrays.asList(60L, 54L, 83L)));
        items.add(new PriorityQueue<>(Arrays.asList(77L, 67L)));
        items.add(new PriorityQueue<>(Arrays.asList(95L, 65L, 58L, 76L)));
        return items;
    }

    private static List<List<Integer>> getTestRules() {
        List<List<Integer>> testRules = new ArrayList<>();
        testRules.add(Arrays.asList(19, 2, 7));
        testRules.add(Arrays.asList(3, 4, 5));
        testRules.add(Arrays.asList(11, 7, 3));
        testRules.add(Arrays.asList(17, 6, 1));
        testRules.add(Arrays.asList(5, 0, 5));
        testRules.add(Arrays.asList(2, 2, 0));
        testRules.add(Arrays.asList(13, 4, 1));
        testRules.add(Arrays.asList(7, 3, 6));
        return testRules;
    }

    private static List<List<String>> getOperations() {
        List<List<String>> operations = new ArrayList<>();
        operations.add(Arrays.asList("*", "13"));
        operations.add(Arrays.asList("+", "2"));
        operations.add(Arrays.asList("+", "1"));
        operations.add(Arrays.asList("+", "8"));
        operations.add(Arrays.asList("*", "old"));
        operations.add(Arrays.asList("+", "4"));
        operations.add(Arrays.asList("*", "17"));
        operations.add(Arrays.asList("+", "5"));
        return operations;
    }
}
