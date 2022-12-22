package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day22 {
    private static List<String> inputBoard;
    private static String inputCommandString;
    private static final int[] startPoint = new int[2];

    public static void main(String[] args) {
        getInputBoardAndCommandStringFromInput();
        final Map<List<Integer>, Character> board = createBoardFromInput();
        final List<String> commands = getListOfCommandsFromCommandString();

        System.out.println("Puzzle 1 Answer = " + puzzle1(board, commands));
    }

    private static int puzzle1(Map<List<Integer>, Character> board, List<String> commands) {
        int dir = 0;
        int[] curr = startPoint;

        for (String command : commands) {
            if (command.equals("L") || command.equals("R")) {
                dir = changeDirection(dir, command);
            } else {
                int val = Integer.parseInt(command);
                while (val-- > 0) {
                    if (!move(board, dir, curr)) {
                        break;
                    }
                }
            }
        }

        return 1000 * curr[0] + 4 * curr[1] + dir;
    }

    private static boolean move(Map<List<Integer>, Character> board, int dir, int[] curr) {
        int r = curr[0], c = curr[1];
        switch (dir) {
            case 0:
                if (!board.containsKey(Arrays.asList(r, c + 1))) {
                    while (board.containsKey(Arrays.asList(r, c))) {
                        c--;
                    }
                }
                c += 1;
                break;
            case 1:
                if (!board.containsKey(Arrays.asList(r + 1, c))) {
                    while (board.containsKey(Arrays.asList(r, c))) {
                        r--;
                    }
                }
                r += 1;
                break;
            case 2:
                if (!board.containsKey(Arrays.asList(r, c - 1))) {
                    while (board.containsKey(Arrays.asList(r, c))) {
                        c++;
                    }
                }
                c -= 1;
                break;
            case 3:
                if (!board.containsKey(Arrays.asList(r - 1, c))) {
                    while (board.containsKey(Arrays.asList(r, c))) {
                        r++;
                    }
                }
                r -= 1;
                break;
            default:
                return false;
        }

        if (board.get(Arrays.asList(r, c)) == '#') {
            return false;
        } else {
            curr[0] = r;
            curr[1] = c;
            return true;
        }
    }

    private static int changeDirection(int dir, String command) {
        if (command.equals("R")) {
            return (dir + 1) % 4;
        } else {
            return (dir - 1 + 4) % 4;
        }
    }

    private static Map<List<Integer>, Character> createBoardFromInput() {
        boolean isStartFound = false;
        final Map<List<Integer>, Character> board = new HashMap<>();
        for (int i = 0; i < inputBoard.size(); i++) {
            for (int j = 0; j < inputBoard.get(i).length(); j++) {
                if (inputBoard.get(i).charAt(j) == '.' || inputBoard.get(i).charAt(j) == '#') {
                    if (i == 0 && !isStartFound) {
                        startPoint[0] = i + 1;
                        startPoint[1] = j + 1;
                        isStartFound = true;
                    }
                    board.put(Arrays.asList(i + 1, j + 1), inputBoard.get(i).charAt(j));
                }
            }
        }
        return board;
    }

    private static List<String> getListOfCommandsFromCommandString() {
        final List<String> commands = new ArrayList<>();
        final int n = inputCommandString.length();

        int i = 0;
        while (i < n) {
            final StringBuilder num = new StringBuilder();
            while (i < n && isDigit(inputCommandString.charAt(i))) {
                num.append(inputCommandString.charAt(i++));
            }
            commands.add(num.toString());

            if (i < n) {
                commands.add(String.valueOf(inputCommandString.charAt(i++)));
            }
        }

        return commands;
    }

    private static boolean isDigit(char ch) {
        return ch - '0' >= 0 && ch - '0' <= 9;
    }

    public static void getInputBoardAndCommandStringFromInput() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please provide your input:");
        inputBoard = new ArrayList<>();
        String input = sc.nextLine();

        while (input.length() > 0) {
            inputBoard.add(input);
            input = sc.nextLine();
        }

        inputCommandString = sc.nextLine();
        sc.close();
    }
}
