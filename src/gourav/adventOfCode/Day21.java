package gourav.adventOfCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day21 {
    private static final Map<String, List<String>> monkeyGraph = new HashMap<>();
    private static final Map<String, Long> monkeyNumbersFromInput = new HashMap<>();

    public static void main(String[] args) {
        populateMapsFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1());
        System.out.println("Puzzle 2 Answer = " + puzzle2());
    }

    private static long puzzle1() {
        final HashMap<String, Long> monkeyNumbers = new HashMap<>(monkeyNumbersFromInput);
        return getMonkeyNumber(monkeyNumbers, "root");
    }

    private static long puzzle2() {
        final long lowerLimit = 3378273300000L;
        final long upperLimit = 3378273400000L;

        for (long human = lowerLimit; human <= upperLimit; human++) {
            final Map<String, Long> monkeyNumbers = new HashMap<>(monkeyNumbersFromInput);
            monkeyNumbers.put("humn", human);

            final List<String> list = monkeyGraph.get("root");
            final long left = getMonkeyNumber(monkeyNumbers, list.get(0));
            final long right = getMonkeyNumber(monkeyNumbers, list.get(1));

            if (left == right) {
                return human;
            }
        }

        return -1;
    }

    private static long getMonkeyNumber(Map<String, Long> monkeyNumbers, String monkey) {
        if (monkeyNumbers.containsKey(monkey)) {
            return monkeyNumbers.get(monkey);
        }

        final List<String> list = monkeyGraph.get(monkey);
        final long left = getMonkeyNumber(monkeyNumbers, list.get(0));
        final long right = getMonkeyNumber(monkeyNumbers, list.get(1));

        switch (list.get(2)) {
            case "+":
                monkeyNumbers.put(monkey, left + right);
                break;
            case "-":
                monkeyNumbers.put(monkey, left - right);
                break;
            case "*":
                monkeyNumbers.put(monkey, left * right);
                break;
            default:
                monkeyNumbers.put(monkey, left / right);
                break;
        }

        return monkeyNumbers.get(monkey);
    }

    private static void populateMapsFromInput() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please provide your input:");
        String input = sc.nextLine();

        while (input.length() > 0) {
            final String monkey = input.split(":")[0];
            final String[] parts = input.split(":")[1].substring(1).split(" ");
            if (parts.length == 1) {
                monkeyNumbersFromInput.put(monkey, Long.valueOf(parts[0]));
            } else {
                monkeyGraph.put(monkey, Arrays.asList(parts[0], parts[2], parts[1]));
            }
            input = sc.nextLine();
        }

        sc.close();
    }
}
