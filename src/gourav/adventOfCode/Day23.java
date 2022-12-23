package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static gourav.adventOfCode.utils.AOCUtils.getListOfStringsFromInput;

public class Day23 {
    public static void main(String[] args) {
        final List<String> input = getListOfStringsFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(input));
    }

    private static int puzzle1(List<String> input) {
        final Set<List<Integer>> elves = new HashSet<>();
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == '#') {
                    elves.add(Arrays.asList(i, j));
                }
            }
        }

        final List<Character> dirs = new ArrayList<>(Arrays.asList('N', 'S', 'W', 'E'));

        for (int round = 0; round < 10; round++) {
            moveElves(elves, dirs);
        }

        return getCountOfEmptyGroundTiles(elves);
    }

    private static void moveElves(Set<List<Integer>> elves, List<Character> dirs) {
        Map<List<Integer>, List<List<Integer>>> map = new HashMap<>();
        for (List<Integer> elf : elves) {
            if (!isThereAnyNeighbourInAnyDirection(elves, elf)) {
                continue;
            }

            for (char dir : dirs) {
                if (dir == 'N') {
                    if (!isThereAnyNeighbourInNorth(elves, elf)) {
                        final int x = elf.get(0) - 1, y = elf.get(1);
                        map.computeIfAbsent(Arrays.asList(x, y), k -> new ArrayList<>()).add(elf);
                        break;
                    }
                }

                if (dir == 'S') {
                    if (!isThereAnyNeighbourInSouth(elves, elf)) {
                        final int x = elf.get(0) + 1, y = elf.get(1);
                        map.computeIfAbsent(Arrays.asList(x, y), k -> new ArrayList<>()).add(elf);
                        break;
                    }
                }

                if (dir == 'W') {
                    if (!isThereAnyNeighbourInWest(elves, elf)) {
                        final int x = elf.get(0), y = elf.get(1) - 1;
                        map.computeIfAbsent(Arrays.asList(x, y), k -> new ArrayList<>()).add(elf);
                        break;
                    }
                }

                if (dir == 'E') {
                    if (!isThereAnyNeighbourInEast(elves, elf)) {
                        final int x = elf.get(0), y = elf.get(1) + 1;
                        map.computeIfAbsent(Arrays.asList(x, y), k -> new ArrayList<>()).add(elf);
                        break;
                    }
                }
            }
        }

        final Character rem = dirs.remove(0);
        dirs.add(rem);

        for (List<Integer> pos : map.keySet()) {
            if (map.get(pos).size() == 1) {
                elves.add(pos);
                elves.remove(map.get(pos).get(0));
            }
        }
    }

    private static int getCountOfEmptyGroundTiles(Set<List<Integer>> elves) {
        int xMin = Integer.MAX_VALUE, xMax = Integer.MIN_VALUE;
        int yMin = Integer.MAX_VALUE, yMax = Integer.MIN_VALUE;

        for (List<Integer> elf : elves) {
            xMin = Math.min(xMin, elf.get(0));
            xMax = Math.max(xMax, elf.get(0));
            yMin = Math.min(yMin, elf.get(1));
            yMax = Math.max(yMax, elf.get(1));
        }

        int count = 0;
        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                if (!elves.contains(Arrays.asList(x, y))) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isThereAnyNeighbourInAnyDirection(Set<List<Integer>> elves, List<Integer> elf) {
        return isThereAnyNeighbourInNorth(elves, elf) ||
                isThereAnyNeighbourInSouth(elves, elf) ||
                isThereAnyNeighbourInWest(elves, elf) ||
                isThereAnyNeighbourInEast(elves, elf);
    }

    private static boolean isThereAnyNeighbourInNorth(Set<List<Integer>> elves, List<Integer> elf) {
        final int[][] dirs = {{-1, 0}, {-1, 1}, {-1, -1}};
        return isThereAnyNeighbour(elves, elf, dirs);
    }

    private static boolean isThereAnyNeighbourInSouth(Set<List<Integer>> elves, List<Integer> elf) {
        final int[][] dirs = {{1, 0}, {1, 1}, {1, -1}};
        return isThereAnyNeighbour(elves, elf, dirs);
    }

    private static boolean isThereAnyNeighbourInWest(Set<List<Integer>> elves, List<Integer> elf) {
        final int[][] dirs = {{0, -1}, {-1, -1}, {1, -1}};
        return isThereAnyNeighbour(elves, elf, dirs);
    }

    private static boolean isThereAnyNeighbourInEast(Set<List<Integer>> elves, List<Integer> elf) {
        final int[][] dirs = {{0, 1}, {-1, 1}, {1, 1}};
        return isThereAnyNeighbour(elves, elf, dirs);
    }

    private static boolean isThereAnyNeighbour(Set<List<Integer>> elves, List<Integer> elf, int[][] dirs) {
        for (int[] dir : dirs) {
            if (elves.contains(Arrays.asList(elf.get(0) + dir[0], elf.get(1) + dir[1]))) {
                return true;
            }
        }
        return false;
    }
}
