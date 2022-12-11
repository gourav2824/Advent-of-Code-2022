package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day9 {
    public static void main(String[] args) {
        final List<List<String>> input = getInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(input));
    }

    private static int puzzle1(List<List<String>> input) {
        Set<List<Integer>> visited = new HashSet<>();
        visited.add(Arrays.asList(0, 0));

        int hr = 0, hc = 0, tr = 0, tc = 0;

        for (List<String> line : input) {
            char ch = line.get(0).charAt(0);
            int steps = Integer.parseInt(line.get(1));

            for (int i = 0; i < steps; i++) {
                if (ch == 'U') {
                    hr -= 1;
                } else if (ch == 'D') {
                    hr += 1;
                } else if (ch == 'L') {
                    hc -= 1;
                } else {
                    hc += 1;
                }

                if (isNear(hr, hc, tr, tc)) {
                    continue;
                }

                if (hr == tr) {
                    if (hc < tc) {
                        tc -= 1;
                    } else {
                        tc += 1;
                    }
                } else if (hc == tc) {
                    if (hr < tr) {
                        tr -= 1;
                    } else {
                        tr += 1;
                    }
                } else {
                    if (hr < tr && hc > tc) {
                        tr -= 1;
                        tc += 1;
                    } else if (hr > tr && hc > tc) {
                        tr += 1;
                        tc += 1;
                    } else if (hr > tr) {
                        tr += 1;
                        tc -= 1;
                    } else {
                        tr -= 1;
                        tc -= 1;
                    }
                }

                visited.add(Arrays.asList(tr, tc));
            }
        }

        return visited.size();
    }

    private static boolean isNear(int hr, int hc, int tr, int tc) {
        int[][] dirs = {{0, 0}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}};
        for (int[] dir : dirs) {
            if (hr + dir[0] == tr && hc + dir[1] == tc) {
                return true;
            }
        }
        return false;
    }

    private static List<List<String>> getInput() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please provide your input:");
        List<List<String>> list = new ArrayList<>();
        String input = sc.nextLine();

        while (input.length() > 0) {
            list.add(Arrays.asList(input.split(" ")[0], input.split(" ")[1]));
            input = sc.nextLine();
        }

        sc.close();
        return list;
    }
}
