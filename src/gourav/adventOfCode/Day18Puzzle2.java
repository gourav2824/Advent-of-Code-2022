package gourav.adventOfCode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import static gourav.adventOfCode.Day18Puzzle1.getListOfCubesFromInput;

public class Day18Puzzle2 {
    public static void main(String[] args) {
        final List<int[]> listOfCubes = getListOfCubesFromInput();
        System.out.println("Puzzle 2 Answer = " + puzzle2(listOfCubes));
    }

    private static int puzzle2(List<int[]> listOfCubes) {
        double xMin = Integer.MAX_VALUE, yMin = Integer.MAX_VALUE, zMin = Integer.MAX_VALUE;
        double xMax = Integer.MIN_VALUE, yMax = Integer.MIN_VALUE, zMax = Integer.MIN_VALUE;

        final Set<List<Double>> droplets = new HashSet<>();
        final Set<List<Double>> allFaces = new HashSet<>();
        final double[][] dirs = {{0.5, 0, 0}, {0, 0.5, 0}, {0, 0, 0.5}, {-0.5, 0, 0}, {0, -0.5, 0}, {0, 0, -0.5}};

        for (int[] cube : listOfCubes) {
            final double x = cube[0], y = cube[1], z = cube[2];
            droplets.add(Arrays.asList(x, y, z));

            for (double[] dir : dirs) {
                allFaces.add(Arrays.asList(x + dir[0], y + dir[1], z + dir[2]));
            }

            xMin = Math.min(xMin, x);
            yMin = Math.min(yMin, y);
            zMin = Math.min(zMin, z);

            xMax = Math.max(xMax, x);
            yMax = Math.max(yMax, y);
            zMax = Math.max(zMax, z);
        }

        final Set<List<Double>> openFaces = getAllOpenFaces(droplets, dirs, xMin - 1, yMin - 1, zMin - 1, xMax + 1, yMax + 1, zMax + 1);

        int count = 0;

        for (List<Double> face : allFaces) {
            if (openFaces.contains(face)) {
                count++;
            }
        }

        return count;
    }

    private static Set<List<Double>> getAllOpenFaces(Set<List<Double>> droplets, double[][] dirs,
                                                     double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
        final Queue<List<Double>> queue = new LinkedList<>();
        queue.add(Arrays.asList(xMin, yMin, zMin));

        final Set<List<Double>> openDroplets = new HashSet<>();
        openDroplets.add(Arrays.asList(xMin, yMin, zMin));
        
        while (queue.size() > 0) {
            final List<Double> rem = queue.remove();

            for (double[] dir : dirs) {
                final double x = rem.get(0) + dir[0] * 2;
                final double y = rem.get(1) + dir[1] * 2;
                final double z = rem.get(2) + dir[2] * 2;

                if (x < xMin || y < yMin || z < zMin || x > xMax || y > yMax || z > zMax) {
                    continue;
                }

                final List<Double> face = Arrays.asList(x, y, z);
                if (droplets.contains(face) || openDroplets.contains(face)) {
                    continue;
                }

                openDroplets.add(face);
                queue.add(face);
            }
        }

        final Set<List<Double>> openFaces = new HashSet<>();

        for (List<Double> droplet : openDroplets) {
            final double x = droplet.get(0), y = droplet.get(1), z = droplet.get(2);
            for (double[] dir : dirs) {
                openFaces.add(Arrays.asList(x + dir[0], y + dir[1], z + dir[2]));
            }
        }

        return openFaces;
    }
}
