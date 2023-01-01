package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import static gourav.adventOfCode.utils.AOCUtils.getListOfStringsFromInput;

public class Day24 {
    private static class Cell {
        int r;
        int c;
        int minutes;

        public Cell(int r, int c, int minute) {
            this.r = r;
            this.c = c;
            this.minutes = minute;
        }
    }

    public static void main(String[] args) {
        final List<String> input = getListOfStringsFromInput();
        System.out.println("Calculating Puzzle 1 Answer...");
        System.out.println("Puzzle 1 Answer = " + puzzle1(input));
    }

    private static int puzzle1(List<String> input) {
        final int m = input.size(), n = input.get(0).length();
        final List<Map<List<Integer>, List<Character>>> blockersTillTimeT = getListOfBlockersMapTillTimeT(input);

        final int[] start = {0, 1};
        final int[] end = {m - 1, n - 2};
        final int initialTime = 0;

        return calculateTimeFromSourceToDestination(m, n, blockersTillTimeT, start, end, initialTime);
    }

    private static int calculateTimeFromSourceToDestination(int m, int n, List<Map<List<Integer>, List<Character>>> blockersTillTimeT,
                                                            int[] start, int[] end, int initialTime) {
        final Set<List<Integer>> visited = new HashSet<>();
        final Queue<Cell> queue = new LinkedList<>();
        queue.add(new Cell(start[0], start[1], initialTime));

        while (queue.size() > 0) {
            final Cell rem = queue.remove();
            final int r = rem.r, c = rem.c, t = rem.minutes;

            if (r < 0 || c < 0 || r >= m || c >= n) {
                continue;
            }

            if (r == end[0] && c == end[1]) {
                return t;
            }

            final Map<List<Integer>, List<Character>> blockersAtT = blockersTillTimeT.get(t);
            if (blockersAtT.containsKey(Arrays.asList(r, c)) && blockersAtT.get(Arrays.asList(r, c)).size() > 0) {
                continue;
            }

            final List<Integer> state = Arrays.asList(r, c, t);
            if (visited.contains(state)) {
                continue;
            }
            visited.add(state);

            queue.add(new Cell(r, c, t + 1));
            queue.add(new Cell(r - 1, c, t + 1));
            queue.add(new Cell(r + 1, c, t + 1));
            queue.add(new Cell(r, c - 1, t + 1));
            queue.add(new Cell(r, c + 1, t + 1));
        }

        return -1;
    }

    private static List<Map<List<Integer>, List<Character>>> getListOfBlockersMapTillTimeT(List<String> input) {
        final int m = input.size(), n = input.get(0).length();
        Map<List<Integer>, List<Character>> blockers = new HashMap<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                final char ch = input.get(i).charAt(j);
                if (ch != '.') {
                    blockers.put(Arrays.asList(i, j), Collections.singletonList(ch));
                }
            }
        }

        final List<Map<List<Integer>, List<Character>>> blockersTillTimeT = new ArrayList<>();
        blockersTillTimeT.add(new HashMap<>(blockers));

        for (int t = 1; t < m * n; t++) {
            blockers = moveBlizzards(blockers, m, n);
            blockersTillTimeT.add(new HashMap<>(blockers));
        }

        return blockersTillTimeT;
    }

    private static Map<List<Integer>, List<Character>> moveBlizzards(Map<List<Integer>, List<Character>> blockers, int m, int n) {
        final Map<List<Integer>, List<Character>> newBlockers = new HashMap<>();

        for (List<Integer> blocker : blockers.keySet()) {
            final int i = blocker.get(0), j = blocker.get(1);
            for (char ch : blockers.get(blocker)) {
                int r, c;
                if (ch == '^') {
                    r = i - 1 > 0 ? i - 1 : m - 2;
                    c = j;
                } else if (ch == 'v') {
                    r = i + 1 < m - 1 ? i + 1 : 1;
                    c = j;
                } else if (ch == '<') {
                    r = i;
                    c = j - 1 > 0 ? j - 1 : n - 2;
                } else if (ch == '>') {
                    r = i;
                    c = j + 1 < n - 1 ? j + 1 : 1;
                } else {
                    r = i;
                    c = j;
                }
                newBlockers.computeIfAbsent(Arrays.asList(r, c), k -> new ArrayList<>()).add(ch);
            }
        }

        return newBlockers;
    }
}
