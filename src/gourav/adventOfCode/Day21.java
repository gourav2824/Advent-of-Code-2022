package gourav.adventOfCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day21 {
    private static final Map<String, List<String>> monkeyGraph = new HashMap<>();
    private static final Map<String, Long> monkeyNumbers = new HashMap<>();

    public static void main(String[] args) {
        populateMapsFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1());
    }

    private static long puzzle1() {
        return getMonkeyNumber("root");
    }

    private static long getMonkeyNumber(String monkey) {
        if (monkeyNumbers.containsKey(monkey)) {
            return monkeyNumbers.get(monkey);
        }

        final List<String> list = monkeyGraph.get(monkey);
        final long left = getMonkeyNumber(list.get(0));
        final long right = getMonkeyNumber(list.get(1));

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
                monkeyNumbers.put(monkey, Long.valueOf(parts[0]));
            } else {
                monkeyGraph.put(monkey, Arrays.asList(parts[0], parts[2], parts[1]));
            }
            input = sc.nextLine();
        }

        sc.close();
    }
}
