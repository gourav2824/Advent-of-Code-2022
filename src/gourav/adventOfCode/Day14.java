package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day14 {
    public static void main(String[] args) {
        final List<List<int[]>> listOfPaths = getListOfPathsFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(listOfPaths));
        System.out.println("Puzzle 2 Answer = " + puzzle2(listOfPaths));
    }

    private static int puzzle1(List<List<int[]>> listOfPaths) {
        final Set<List<Integer>> cave = new HashSet<>();
        int xMin = Integer.MAX_VALUE, xMax = Integer.MIN_VALUE, yMax = Integer.MIN_VALUE;

        for (List<int[]> path : listOfPaths) {
            for (int i = 1; i < path.size(); i++) {
                final int[] point1 = path.get(i - 1);
                final int[] point2 = path.get(i);
                drawLine(cave, point1, point2);

                xMin = Math.min(xMin, Math.min(point1[0], point2[0]));
                xMax = Math.max(xMax, Math.max(point1[0], point2[0]));
                yMax = Math.max(yMax, Math.max(point1[1], point2[1]));
            }
        }

        int units = 0;

        while (true) {
            final int[] sand = {500, 0};
            boolean cameToRest = false;

            while (!cameToRest && sand[0] >= xMin && sand[0] <= xMax && sand[1] <= yMax) {
                cameToRest = moveSand(cave, sand);
            }

            if (cameToRest) {
                units++;
            }

            if (sand[0] < xMin || sand[0] > xMax || sand[1] > yMax) {
                break;
            }
        }

        return units;
    }

    private static int puzzle2(List<List<int[]>> listOfPaths) {
        final Set<List<Integer>> cave = new HashSet<>();
        int yMax = Integer.MIN_VALUE;

        for (List<int[]> path : listOfPaths) {
            for (int i = 1; i < path.size(); i++) {
                final int[] point1 = path.get(i - 1);
                final int[] point2 = path.get(i);
                drawLine(cave, point1, point2);

                yMax = Math.max(yMax, Math.max(point1[1], point2[1]));
            }
        }

        int units = 0;

        while (true) {
            final int[] sand = {500, 0};
            boolean cameToRest = false;

            while (!cameToRest && sand[1] <= yMax) {
                cameToRest = moveSand(cave, sand);
            }

            if (cameToRest) {
                units++;
                if (sand[0] == 500 && sand[1] == 0) {
                    break;
                }
            }

            if (sand[1] == yMax + 1) {
                cave.add(Arrays.asList(sand[0], sand[1]));
                units++;
            }
        }

        return units;
    }

    private static boolean moveSand(Set<List<Integer>> cave, int[] sand) {
        boolean cameToRest = false;
        if (!cave.contains(Arrays.asList(sand[0], sand[1] + 1))) {
            sand[1] += 1;
        } else if (!cave.contains(Arrays.asList(sand[0] - 1, sand[1] + 1))) {
            sand[0] -= 1;
            sand[1] += 1;
        } else if (!cave.contains(Arrays.asList(sand[0] + 1, sand[1] + 1))) {
            sand[0] += 1;
            sand[1] += 1;
        } else {
            cave.add(Arrays.asList(sand[0], sand[1]));
            cameToRest = true;
        }
        return cameToRest;
    }

    private static void drawLine(Set<List<Integer>> cave, int[] point1, int[] point2) {
        if (point1[0] == point2[0]) {
            final int x = point1[0];
            for (int y = Math.min(point1[1], point2[1]); y <= Math.max(point1[1], point2[1]); y++) {
                cave.add(Arrays.asList(x, y));
            }
        } else {
            final int y = point1[1];
            for (int x = Math.min(point1[0], point2[0]); x <= Math.max(point1[0], point2[0]); x++) {
                cave.add(Arrays.asList(x, y));
            }
        }
    }

    private static List<List<int[]>> getListOfPathsFromInput() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please provide your input:");
        List<List<int[]>> paths = new ArrayList<>();
        String input = sc.nextLine();

        while (input.length() > 0) {
            final List<int[]> path = new ArrayList<>();
            for (String point : input.split(" -> ")) {
                int x = Integer.parseInt(point.split(",")[0]);
                int y = Integer.parseInt(point.split(",")[1]);
                path.add(new int[]{x, y});
            }
            paths.add(path);
            input = sc.nextLine();
        }

        sc.close();
        return paths;
    }
}
