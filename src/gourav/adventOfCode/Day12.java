package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Day12 {
    public static void main(String[] args) {
        final List<List<Character>> grid = getGridFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(grid));
        System.out.println("Puzzle 2 Answer = " + puzzle2(grid));
    }

    private static int puzzle1(List<List<Character>> grid) {
        final Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                if (grid.get(i).get(j) == 'S') {
                    queue.add(new int[]{i, j, 0});
                    break;
                }
            }
        }
        return bfs(grid, queue);
    }

    private static int puzzle2(List<List<Character>> grid) {
        final Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                if (grid.get(i).get(j) == 'S' || grid.get(i).get(j) == 'a') {
                    queue.add(new int[]{i, j, 0});
                }
            }
        }
        return bfs(grid, queue);
    }

    private static int bfs(List<List<Character>> grid, Queue<int[]> queue) {
        final int m = grid.size(), n = grid.get(0).size();
        final boolean[][] visited = new boolean[m][n];

        while (queue.size() > 0) {
            final int[] rem = queue.remove();
            final int r = rem[0], c = rem[1], level = rem[2];

            if (grid.get(r).get(c) == 'E') {
                return level;
            }

            if (visited[r][c]) {
                continue;
            }
            visited[r][c] = true;

            final int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

            for (int[] dir : dirs) {
                int i = r + dir[0], j = c + dir[1];

                if (i < 0 || j < 0 || i >= m || j >= n || visited[i][j]) {
                    continue;
                }

                if (height(grid.get(r).get(c)) >= height(grid.get(i).get(j)) - 1) {
                    queue.add(new int[]{i, j, level + 1});
                }
            }
        }

        return -1;
    }

    private static int height(char ch) {
        if (ch == 'S') return 0;
        else if (ch == 'E') return 'z' - 'a';
        else return ch - 'a';
    }

    private static List<List<Character>> getGridFromInput() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please provide your input:");
        List<List<Character>> grid = new ArrayList<>();
        String input = sc.nextLine();

        while (input.length() > 0) {
            final List<Character> row = new ArrayList<>();
            for (char ch : input.toCharArray()) {
                row.add(ch);
            }
            grid.add(row);
            input = sc.nextLine();
        }

        sc.close();
        return grid;
    }
}
