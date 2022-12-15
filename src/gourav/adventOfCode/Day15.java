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
        System.out.println("Puzzle 1 Answer = " + puzzle1(sensorsAndBeacons));
    }

    private static int puzzle1(List<List<int[]>> sensorsAndBeacons) {
        final List<int[]> sensors = sensorsAndBeacons.get(0);
        final List<int[]> beacons = sensorsAndBeacons.get(1);
        final int n = sensors.size(), y = 2000000;
        int count = 0;

        final Set<List<Integer>> beaconsSet = new HashSet<>();
        for (int[] beacon : beacons) {
            beaconsSet.add(Arrays.asList(beacon[0], beacon[1]));
        }

        for (int x = -10000000; x <= 10000000; x++) {
            if (beaconsSet.contains(Arrays.asList(x, y))) {
                continue;
            }

            for (int i = 0; i < n; i++) {
                if (distance(sensors.get(i), new int[]{x, y}) <= distance(sensors.get(i), beacons.get(i))) {
                    count++;
                    break;
                }
            }
        }

        return count;
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
            sensor[0] = Integer.parseInt(sensorX.substring(0, sensorX.length() - 1));

            final String sensorY = input.split(" ")[3].split("=")[1];
            sensor[1] = Integer.parseInt(sensorY.substring(0, sensorY.length() - 1));

            final String beaconX = input.split(" ")[8].split("=")[1];
            beacon[0] = Integer.parseInt(beaconX.substring(0, beaconX.length() - 1));

            final String beaconY = input.split(" ")[9].split("=")[1];
            beacon[1] = Integer.parseInt(beaconY);

            sensors.add(sensor);
            beacons.add(beacon);

            input = sc.nextLine();
        }

        final List<List<int[]>> sensorsAndBeacons = Arrays.asList(sensors, beacons);

        sc.close();
        return sensorsAndBeacons;
    }
}
