package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Day18 {
    public static void main(String[] args) {
        final List<int[]> listOfCubes = getListOfCubesFromInput();
        System.out.println("Puzzle 2 Answer = " + puzzle2(listOfCubes));
    }

    private static int puzzle2(List<int[]> listOfCubes) {
        int xMin = Integer.MAX_VALUE, yMin = Integer.MAX_VALUE, zMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE, yMax = Integer.MIN_VALUE, zMax = Integer.MIN_VALUE;

        final Set<List<Integer>> droplets = new HashSet<>();
        final Set<List<Integer>> allFaces = new HashSet<>();
        int[][] dirs = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}, {-1, 0, 0}, {0, -1, 0}, {0, 0, -1}};

        for (int[] cube : listOfCubes) {
            int x = cube[0], y = cube[1], z = cube[2];
            droplets.add(Arrays.asList(x, y, z));

            for (int[] dir : dirs) {
                allFaces.add(Arrays.asList(x + dir[0], y + dir[1], z + dir[2]));
            }

            xMin = Math.min(xMin, x);
            yMin = Math.min(yMin, y);
            zMin = Math.min(zMin, z);

            xMax = Math.max(xMax, x);
            yMax = Math.max(yMax, y);
            zMax = Math.max(zMax, z);
        }

        final Set<List<Integer>> openFaces = getAllOpenFaces(droplets, dirs, xMin - 1, yMin - 1, zMin - 1, xMax + 1, yMax + 1, zMax + 1);

        int count = 0;

        for (List<Integer> face : allFaces) {
            if (openFaces.contains(face)) {
                count++;
            }
        }

        return count;
    }

    private static Set<List<Integer>> getAllOpenFaces(Set<List<Integer>> droplets, int[][] dirs,
                                                      int xMin, int yMin, int zMin, int xMax, int yMax, int zMax) {
        final Queue<List<Integer>> queue = new LinkedList<>();
        queue.add(Arrays.asList(xMin, yMin, zMin));

        final Set<List<Integer>> openDroplets = new HashSet<>();
        openDroplets.add(Arrays.asList(xMin, yMin, zMin));
        
        while (queue.size() > 0) {
            final List<Integer> rem = queue.remove();

            for (int[] dir : dirs) {
                final int x = rem.get(0) + dir[0];
                final int y = rem.get(1) + dir[1];
                final int z = rem.get(2) + dir[2];

                if (x < xMin || y < yMin || z < zMin || x > xMax || y > yMax || z > zMax) {
                    continue;
                }

                final List<Integer> face = Arrays.asList(x, y, z);
                if (droplets.contains(face) || openDroplets.contains(face)) {
                    continue;
                }

                openDroplets.add(face);
                queue.add(face);
            }
        }

        final Set<List<Integer>> openFaces = new HashSet<>();

        for (List<Integer> droplet : openDroplets) {
            for (int[] dir : dirs) {
                final List<Integer> face = Arrays.asList(droplet.get(0) + dir[0], droplet.get(1) + dir[1], droplet.get(2) + dir[2]);
                openFaces.add(face);
            }
        }

        return openFaces;
    }

    public static List<int[]> getListOfCubesFromInput() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please provide your input:");
        final List<int[]> list = new ArrayList<>();
        String input = sc.nextLine();

        while (input.length() > 0) {
            final String[] parts = input.split(",");
            list.add(new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])});
            input = sc.nextLine();
        }

        sc.close();
        return list;
    }
}
