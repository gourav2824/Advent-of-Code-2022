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

        for (String pairs : list) {
            String range1 = pairs.split(",")[0];
            String range2 = pairs.split(",")[1];

            final int[] pair1 = {Integer.parseInt(range1.split("-")[0]), Integer.parseInt(range1.split("-")[1])};
            final int[] pair2 = {Integer.parseInt(range2.split("-")[0]), Integer.parseInt(range2.split("-")[1])};

            if (isFullyContained(pair1, pair2)) {
                count++;
            }
        }

        return count;
    }

    private static int puzzle2(List<String> list) {
        int count = 0;

        for (String pairs : list) {
            String range1 = pairs.split(",")[0];
            String range2 = pairs.split(",")[1];

            final int[] pair1 = {Integer.parseInt(range1.split("-")[0]), Integer.parseInt(range1.split("-")[1])};
            final int[] pair2 = {Integer.parseInt(range2.split("-")[0]), Integer.parseInt(range2.split("-")[1])};

            if (!isSeparate(pair1, pair2)) {
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
}
