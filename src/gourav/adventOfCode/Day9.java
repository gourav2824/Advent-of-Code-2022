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
    }

    private static int puzzle1(List<List<String>> input) {
        final Set<List<Integer>> visited = new HashSet<>();
        visited.add(Arrays.asList(0, 0));

        final Map<String, List<Integer>> directions = getDirectionsMap();
        int hr = 0, hc = 0, tr = 0, tc = 0;

        for (List<String> line : input) {
            final List<Integer> dir = directions.get(line.get(0));
            final int steps = Integer.parseInt(line.get(1));

            for (int i = 0; i < steps; i++) {
                hr += dir.get(0);
                hc += dir.get(1);

                if (isNear(hr, hc, tr, tc)) {
                    continue;
                }

                if (hr == tr) {
                    tc += (hc < tc) ? -1 : 1;
                } else if (hc == tc) {
                    tr += (hr < tr) ? -1 : 1;
                } else {
                    tr += (hr < tr) ? -1 : 1;
                    tc += (hc < tc) ? -1 : 1;
                }

                visited.add(Arrays.asList(tr, tc));
            }
        }

        return visited.size();
    }

    private static boolean isNear(int hr, int hc, int tr, int tc) {
        return (Math.abs(hr - tr) <= 1) && (Math.abs(hc - tc) <= 1);
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
