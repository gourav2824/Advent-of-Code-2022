package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day9 {
    public static void main(String[] args) {
        final List<List<String>> input = getInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(input));
        System.out.println("Puzzle 2 Answer = " + puzzle2(input));
    }

    private static int puzzle1(List<List<String>> input) {
        final Map<String, List<Integer>> directions = getDirectionsMap();
        final int[] head = new int[2];
        final int[] tail = new int[2];

        final Set<List<Integer>> visited = new HashSet<>();
        visited.add(Arrays.asList(tail[0], tail[1]));

        for (List<String> line : input) {
            final List<Integer> dir = directions.get(line.get(0));
            final int steps = Integer.parseInt(line.get(1));

            for (int i = 0; i < steps; i++) {
                head[0] += dir.get(0);
                head[1] += dir.get(1);
                updateTail(head, tail);
                visited.add(Arrays.asList(tail[0], tail[1]));
            }
        }

        return visited.size();
    }

    private static int puzzle2(List<List<String>> input) {
        final Map<String, List<Integer>> directions = getDirectionsMap();
        final int n = 10;
        final int[][] knots = new int[n][2];

        final Set<List<Integer>> visited = new HashSet<>();
        visited.add(Arrays.asList(knots[n - 1][0], knots[n - 1][1]));

        for (List<String> line : input) {
            final List<Integer> dir = directions.get(line.get(0));
            final int steps = Integer.parseInt(line.get(1));

            for (int i = 0; i < steps; i++) {
                knots[0][0] += dir.get(0);
                knots[0][1] += dir.get(1);
                for (int knot = 1; knot < n; knot++) {
                    updateTail(knots[knot - 1], knots[knot]);
                }
                visited.add(Arrays.asList(knots[n - 1][0], knots[n - 1][1]));
            }
        }

        return visited.size();
    }

    private static void updateTail(int[] head, int[] tail) {
        if (!isNear(head, tail)) {
            if (head[0] == tail[0]) {
                tail[1] += (head[1] < tail[1]) ? -1 : 1;
            } else if (head[1] == tail[1]) {
                tail[0] += (head[0] < tail[0]) ? -1 : 1;
            } else {
                tail[0] += (head[0] < tail[0]) ? -1 : 1;
                tail[1] += (head[1] < tail[1]) ? -1 : 1;
            }
        }
    }

    private static boolean isNear(int[] head, int[] tail) {
        return (Math.abs(head[0] - tail[0]) <= 1) && (Math.abs(head[1] - tail[1]) <= 1);
    }

    private static Map<String, List<Integer>> getDirectionsMap() {
        final Map<String, List<Integer>> directions = new HashMap<>();
        directions.put("U", Arrays.asList(-1, 0));
        directions.put("D", Arrays.asList(1, 0));
        directions.put("L", Arrays.asList(0, -1));
        directions.put("R", Arrays.asList(0, 1));
        return directions;
    }

    private static List<List<String>> getInput() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please provide your input:");
        List<List<String>> list = new ArrayList<>();
        String input = sc.nextLine();

        while (input.length() > 0) {
            list.add(Arrays.asList(input.split(" ")[0], input.split(" ")[1]));
            input = sc.nextLine();
        }

        sc.close();
        return list;
    }
}
