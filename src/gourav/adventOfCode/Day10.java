package gourav.adventOfCode;

import java.util.Arrays;
import java.util.List;

import static gourav.adventOfCode.utils.AOCUtils.getListOfStringsFromInput;

public class Day10 {
    public static void main(String[] args) {
        final List<String> commands = getListOfStringsFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(commands));
        System.out.println("Puzzle 2 Answer:");
        displayCRT(puzzle2(commands));
    }

    private static int puzzle1(List<String> commands) {
        final List<Integer> cycles = Arrays.asList(20, 60, 100, 140, 180, 220);
        int x = 1, cycle = 0, sum = 0;

        for (String command : commands) {
            cycle++;
            if (cycles.contains(cycle)) {
                sum += x * cycle;
            }

            if (!command.equals("noop")) {
                cycle++;
                if (cycles.contains(cycle)) {
                    sum += x * cycle;
                }
                x += Integer.parseInt(command.split(" ")[1]);
            }
        }

        return sum;
    }

    private static char[][] puzzle2(List<String> commands) {
        final int m = 6, n = 40;
        final char[][] crt = new char[m][n];
        int x = 1, cycle = -1;

        for (String command : commands) {
            cycle++;
            if (cycle == m * n) break;
            drawPixel(crt, cycle, x);

            if (!command.equals("noop")) {
                cycle++;
                if (cycle == m * n) break;
                drawPixel(crt, cycle, x);
                x += Integer.parseInt(command.split(" ")[1]);
            }
        }

        return crt;
    }

    private static void drawPixel(char[][] crt, int cycle, int x) {
        final int n = 40;
        if (Math.abs((cycle % n) - x) <= 1) {
            crt[cycle / n][cycle % n] = '#';
        } else {
            crt[cycle / n][cycle % n] = '.';
        }
    }

    private static void displayCRT(char[][] crt) {
        for (char[] row : crt) {
            for (char pixel : row) {
                System.out.print(pixel);
            }
            System.out.println();
        }
    }
}
