package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static gourav.adventOfCode.utils.AOCUtils.getListOfStringsFromInput;

public class Day24 {
    private static class Cell {
        int r;
        int c;
        List<List<List<Character>>> board;
        int minutes;

        public Cell(int r, int c, List<List<List<Character>>> board, int minute) {
            this.r = r;
            this.c = c;
            this.board = board;
            this.minutes = minute;
        }
    }

    public static void main(String[] args) {
        final List<String> input = getListOfStringsFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(input));
    }

    private static int puzzle1(List<String> input) {
        final List<List<List<Character>>> board = new ArrayList<>();

        for (String line : input) {
            List<List<Character>> row = new ArrayList<>();
            for (char ch : line.toCharArray()) {
                List<Character> cell = new ArrayList<>();
                if (ch != '.') cell.add(ch);
                row.add(cell);
            }
            board.add(row);
        }

        final int m = board.size(), n = board.get(0).size();
        Queue<Cell> queue = new LinkedList<>();
        queue.add(new Cell(0, 1, board, 0));

        while (queue.size() > 0) {
            final Cell rem = queue.remove();
            final int r = rem.r, c = rem.c;

            if (r < 0 || c < 0 || r >= m || c >= n || rem.board.get(r).get(c).size() > 0) {
                continue;
            }

            if (r == m - 1 && c == n - 2) {
                return rem.minutes;
            }

            final List<List<List<Character>>> newBoard = moveBlizzards(rem.board);

            queue.add(new Cell(r, c, newBoard, rem.minutes + 1));
            queue.add(new Cell(r - 1, c, newBoard, rem.minutes + 1));
            queue.add(new Cell(r + 1, c, newBoard, rem.minutes + 1));
            queue.add(new Cell(r, c - 1, newBoard, rem.minutes + 1));
            queue.add(new Cell(r, c + 1, newBoard, rem.minutes + 1));
        }

        return -1;
    }

    private static void display(List<List<List<Character>>> board) {
        for (List<List<Character>> row : board) {
            for (List<Character> cell : row) {
                System.out.print("[");
                for (char ch : cell) {
                    System.out.print(ch + " ");
                }
                System.out.print("]");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static List<List<List<Character>>> moveBlizzards(List<List<List<Character>>> board) {
        final int m = board.size(), n = board.get(0).size();
        final List<List<List<Character>>> newBoard = getNewEmptyBoard(m, n);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (char ch : board.get(i).get(j)) {
                    if (ch == '#') {
                        newBoard.get(i).get(j).add(ch);
                    } else if (ch == '^') {
                        final int r = i - 1 > 0 ? i - 1 : m - 2;
                        newBoard.get(r).get(j).add(ch);
                    } else if (ch == 'v') {
                        final int r = i + 1 < m - 1 ? i + 1 : 1;
                        newBoard.get(r).get(j).add(ch);
                    } else if (ch == '<') {
                        final int c = j - 1 > 0 ? j - 1 : n - 2;
                        newBoard.get(i).get(c).add(ch);
                    } else if (ch == '>') {
                        final int c = j + 1 < n - 1 ? j + 1 : 1;
                        newBoard.get(i).get(c).add(ch);
                    }
                }
            }
        }

        return newBoard;
    }

    private static List<List<List<Character>>> getNewEmptyBoard(int m, int n) {
        final List<List<List<Character>>> newBoard = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            List<List<Character>> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(new ArrayList<>());
            }
            newBoard.add(row);
        }
        return newBoard;
    }
}
