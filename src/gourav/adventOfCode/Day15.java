package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day15 {
    public static void main(String[] args) {
        final List<List<int[]>> sensorsAndBeacons = getListOfSensorsAndBeaconsFromInput();
        System.out.println("Calculating...");
        System.out.println("Puzzle 1 Answer = " + puzzle1(sensorsAndBeacons.get(0), sensorsAndBeacons.get(1)));
        System.out.println("Puzzle 2 Answer = " + puzzle2(sensorsAndBeacons.get(0), sensorsAndBeacons.get(1)));
    }

    private static int puzzle1(List<int[]> sensors, List<int[]> beacons) {
        final int y = 2000000;
        int count = 0;

        final Set<List<Integer>> beaconsSet = new HashSet<>();
        for (int[] beacon : beacons) {
            beaconsSet.add(Arrays.asList(beacon[0], beacon[1]));
        }

        for (int x = -10000000; x <= 10000000; x++) {
            if (!beaconsSet.contains(Arrays.asList(x, y)) && isDetectedByAnySensor(sensors, beacons, x, y)) {
                count++;
            }
        }
        return count;
    }

    private static long puzzle2(List<int[]> sensors, List<int[]> beacons) {
        for (int i = 0; i < sensors.size(); i++) {
            final int d = distance(sensors.get(i), beacons.get(i));
            for (int xVal = 0; xVal <= d + 1; xVal++) {
                int yVal = (d + 1) - xVal;
                final int[][] signs = {{1, 1}, {-1, 1}, {-1, -1}, {1, -1}};

                for (int[] sign : signs) {
                    final int x = sign[0] * xVal + sensors.get(i)[0];
                    final int y = sign[1] * yVal + sensors.get(i)[1];

                    if (x >= 0 && x <= 4000000 && y >= 0 && y <= 4000000 && !isDetectedByAnySensor(sensors, beacons, x, y)) {
                        return x * 4000000L + y;
                    }
                }
            }
        }
        return 0;
    }

    private static boolean isDetectedByAnySensor(List<int[]> sensors, List<int[]> beacons, int x, int y) {
        for (int i = 0; i < sensors.size(); i++) {
            if (distance(sensors.get(i), new int[]{x, y}) <= distance(sensors.get(i), beacons.get(i))) {
                return true;
            }
        }
        return false;
    }

    private static int distance(int[] point1, int[] point2) {
        return Math.abs(point1[0] - point2[0]) + Math.abs(point1[1] - point2[1]);
    }

    private static List<List<int[]>> getListOfSensorsAndBeaconsFromInput() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please provide your input:");
        final List<int[]> sensors = new ArrayList<>();
        final List<int[]> beacons = new ArrayList<>();
        String input = sc.nextLine();

        while (input.length() > 0) {
            final int[] sensor = new int[2];
            final int[] beacon = new int[2];

            final String sensorX = input.split(" ")[2].split("=")[1];
            final String sensorY = input.split(" ")[3].split("=")[1];
            final String beaconX = input.split(" ")[8].split("=")[1];
            final String beaconY = input.split(" ")[9].split("=")[1];

            sensor[0] = Integer.parseInt(sensorX.substring(0, sensorX.length() - 1));
            sensor[1] = Integer.parseInt(sensorY.substring(0, sensorY.length() - 1));
            beacon[0] = Integer.parseInt(beaconX.substring(0, beaconX.length() - 1));
            beacon[1] = Integer.parseInt(beaconY);

            sensors.add(sensor);
            beacons.add(beacon);
            input = sc.nextLine();
        }

        sc.close();
        return Arrays.asList(sensors, beacons);
    }
}
