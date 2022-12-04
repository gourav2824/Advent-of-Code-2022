package gourav.adventOfCode;

import java.util.List;

import static gourav.adventOfCode.utils.AOCUtils.getListOfStringsFromInput;

public class Day4 {
    public static void main(String[] args) {
        final List<String> list = getListOfStringsFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(list));
        System.out.println("Puzzle 2 Answer = " + puzzle2(list));
    }

    private static int puzzle1(List<String> list) {
        int count = 0;
        for (String ranges : list) {
            int[][] pairs = getPairs(ranges);
            if (isFullyContained(pairs[0], pairs[1])) {
                count++;
            }
        }
        return count;
    }

    private static int puzzle2(List<String> list) {
        int count = 0;
        for (String ranges : list) {
            int[][] pairs = getPairs(ranges);
            if (!isSeparate(pairs[0], pairs[1])) {
                count++;
            }
        }
        return count;
    }

    private static boolean isFullyContained(int[] pair1, int[] pair2) {
        return pair1[0] <= pair2[0] && pair2[1] <= pair1[1] || pair2[0] <= pair1[0] && pair1[1] <= pair2[1];
    }

    private static boolean isSeparate(int[] pair1, int[] pair2) {
        return pair1[1] < pair2[0] || pair2[1] < pair1[0];
    }

    private static int[][] getPairs(String ranges) {
        String range1 = ranges.split(",")[0];
        String range2 = ranges.split(",")[1];

        final int[] pair1 = {Integer.parseInt(range1.split("-")[0]), Integer.parseInt(range1.split("-")[1])};
        final int[] pair2 = {Integer.parseInt(range2.split("-")[0]), Integer.parseInt(range2.split("-")[1])};

        return new int[][]{pair1, pair2};
    }
}
