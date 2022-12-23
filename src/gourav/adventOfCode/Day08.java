package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day08 {
    public static void main(String[] args) {
        final List<List<Integer>> trees = getTreesFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(trees));
        System.out.println("Puzzle 2 Answer = " + puzzle2(trees));
    }

    private static int puzzle1(List<List<Integer>> trees) {
        int count = 0;
        final int m = trees.size(), n = trees.get(0).size();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isVisible(trees, i, j)) count++;
            }
        }

        return count;
    }

    private static int puzzle2(List<List<Integer>> trees) {
        int max = 0;
        final int m = trees.size(), n = trees.get(0).size();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                max = Math.max(max, getScenicScore(trees, i, j));
            }
        }

        return max;
    }

    private static boolean isVisible(List<List<Integer>> trees, int r, int c) {
        int val = trees.get(r).get(c);

        boolean found = false;
        for (int i = 0; i < r; i++) {
            if (trees.get(i).get(c) >= val) {
                found = true;
                break;
            }
        }
        if (!found) return true;

        found = false;
        for (int i = r + 1; i < trees.size(); i++) {
            if (trees.get(i).get(c) >= val) {
                found = true;
                break;
            }
        }
        if (!found) return true;

        found = false;
        for (int i = 0; i < c; i++) {
            if (trees.get(r).get(i) >= val) {
                found = true;
                break;
            }
        }
        if (!found) return true;

        found = false;
        for (int i = c + 1; i < trees.get(0).size(); i++) {
            if (trees.get(r).get(i) >= val) {
                found = true;
                break;
            }
        }

        return !found;
    }

    private static int getScenicScore(List<List<Integer>> trees, int r, int c) {
        int val = trees.get(r).get(c);
        int up = 0, down = 0, left = 0, right = 0;

        for (int i = r - 1; i >= 0; i--) {
            up++;
            if (trees.get(i).get(c) >= val) break;
        }

        for (int i = r + 1; i < trees.size(); i++) {
            down++;
            if (trees.get(i).get(c) >= val) break;
        }

        for (int i = c - 1; i >= 0; i--) {
            left++;
            if (trees.get(r).get(i) >= val) break;
        }

        for (int i = c + 1; i < trees.get(0).size(); i++) {
            right++;
            if (trees.get(r).get(i) >= val) break;
        }

        return up * down * left * right;
    }

    private static List<List<Integer>> getTreesFromInput() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please provide your input:");
        List<List<Integer>> trees = new ArrayList<>();
        String input = sc.nextLine();

        while (input.length() > 0) {
            List<Integer> row = new ArrayList<>();
            for (char ch : input.toCharArray()) {
                row.add(Integer.valueOf(ch + ""));
            }
            trees.add(row);
            input = sc.nextLine();
        }

        sc.close();
        return trees;
    }
}
