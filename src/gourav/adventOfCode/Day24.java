package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

import static gourav.adventOfCode.utils.AOCUtils.getListOfStringsFromInput;

public class Day24 {
    private static class Cell {
        int r;
        int c;
        int time;
        boolean isJourney1Done;
        boolean isJourney2Done;

        public Cell(int r, int c, int minutes, boolean isJourney1Done, boolean isJourney2Done) {
            this.r = r;
            this.c = c;
            this.time = minutes;
            this.isJourney1Done = isJourney1Done;
            this.isJourney2Done = isJourney2Done;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Cell cell = (Cell) o;
            return r == cell.r && c == cell.c && time == cell.time && isJourney1Done == cell.isJourney1Done && isJourney2Done == cell.isJourney2Done;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c, time, isJourney1Done, isJourney2Done);
        }
    }

    public static void main(String[] args) {
        final List<String> input = getListOfStringsFromInput();
        final int m = input.size(), n = input.get(0).length();

        System.out.println("Pre-processing...");
        final List<Map<List<Integer>, List<Character>>> blockersTillTimeT = getListOfBlockersMapTillTimeT(input);

        System.out.println("Calculating Puzzle 1 Answer...");
        System.out.println("Puzzle 1 Answer = " + puzzle1(m, n, blockersTillTimeT));

        System.out.println("Calculating Puzzle 2 Answer...");
        System.out.println("Puzzle 2 Answer = " + puzzle2(m, n, blockersTillTimeT));
    }

    private static int puzzle1(int m, int n, List<Map<List<Integer>, List<Character>>> blockersTillTimeT) {
        final int[] start = {0, 1};
        final int[] end = {m - 1, n - 2};

        final Set<List<Integer>> visited = new HashSet<>();
        final Queue<List<Integer>> queue = new LinkedList<>();
        queue.add(Arrays.asList(start[0], start[1], 0));

        while (queue.size() > 0) {
            final List<Integer> rem = queue.remove();
            final int r = rem.get(0), c = rem.get(1), t = rem.get(2);

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

            queue.add(Arrays.asList(r, c, t + 1));
            queue.add(Arrays.asList(r - 1, c, t + 1));
            queue.add(Arrays.asList(r + 1, c, t + 1));
            queue.add(Arrays.asList(r, c - 1, t + 1));
            queue.add(Arrays.asList(r, c + 1, t + 1));
        }

        return -1;
    }

    private static int puzzle2(int m, int n, List<Map<List<Integer>, List<Character>>> blockersTillTimeT) {
        final int[] start = {0, 1};
        final int[] end = {m - 1, n - 2};

        final Set<Cell> visited = new HashSet<>();
        final Queue<Cell> queue = new LinkedList<>();
        queue.add(new Cell(start[0], start[1], 0, false, false));

        while (queue.size() > 0) {
            final Cell rem = queue.remove();
            final int r = rem.r, c = rem.c, t = rem.time;
            boolean isJourney1Done = rem.isJourney1Done, isJourney2Done = rem.isJourney2Done;

            if (r < 0 || c < 0 || r >= m || c >= n) {
                continue;
            }

            if (!isJourney1Done && r == end[0] && c == end[1]) {
                isJourney1Done = true;
            }

            if (isJourney1Done && r == 0 && c == 1) {
                isJourney2Done = true;
            }

            if (isJourney2Done && r == end[0] && c == end[1]) {
                return t;
            }

            final Map<List<Integer>, List<Character>> blockersAtT = blockersTillTimeT.get(t);
            if (blockersAtT.containsKey(Arrays.asList(r, c)) && blockersAtT.get(Arrays.asList(r, c)).size() > 0) {
                continue;
            }

            if (visited.contains(rem)) {
                continue;
            }
            visited.add(rem);

            queue.add(new Cell(r, c, t + 1, isJourney1Done, isJourney2Done));
            queue.add(new Cell(r - 1, c, t + 1, isJourney1Done, isJourney2Done));
            queue.add(new Cell(r + 1, c, t + 1, isJourney1Done, isJourney2Done));
            queue.add(new Cell(r, c - 1, t + 1, isJourney1Done, isJourney2Done));
            queue.add(new Cell(r, c + 1, t + 1, isJourney1Done, isJourney2Done));
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

        for (int t = 1; t < 2 * m * n; t++) {
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
