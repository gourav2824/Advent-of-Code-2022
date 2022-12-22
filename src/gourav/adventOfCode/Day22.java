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
    private static int n;

    public static void main(String[] args) {
        getInputBoardAndCommandStringFromInput();
        final Map<List<Integer>, Character> board = createBoardFromInput();
        final List<String> commands = getListOfCommandsFromCommandString();

        System.out.println("Puzzle 1 Answer = " + puzzle1(board, commands));
        System.out.println("Puzzle 2 Answer = " + puzzle2(board, commands));
    }

    private static int puzzle1(Map<List<Integer>, Character> board, List<String> commands) {
        int dir = 0;
        int[] curr = new int[]{startPoint[0], startPoint[1]};

        for (String command : commands) {
            if (command.equals("L") || command.equals("R")) {
                dir = changeDirection(dir, command);
            } else {
                int val = Integer.parseInt(command);
                while (val-- > 0) {
                    if (!moveAroundTheBoard(board, dir, curr)) {
                        break;
                    }
                }
            }
        }

        return 1000 * curr[0] + 4 * curr[1] + dir;
    }

    private static boolean moveAroundTheBoard(Map<List<Integer>, Character> board, int dir, int[] curr) {
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

    // The puzzle 2 solution will only work for a specific cube having the following shape:
    //          . . 1 .
    //          2 3 4 .
    //          . . 5 6
    // The above shown cube faces are handled separately in the solution.

    private static int puzzle2(Map<List<Integer>, Character> board, List<String> commands) {
        int[] curr = new int[]{startPoint[0], startPoint[1], 0};

        for (String command : commands) {
            if (command.equals("L") || command.equals("R")) {
                curr[2] = changeDirection(curr[2], command);
            } else {
                int val = Integer.parseInt(command);
                while (val-- > 0) {
                    if (!moveAroundTheCube(board, curr)) {
                        break;
                    }
                }
            }
        }

        return 1000 * curr[0] + 4 * curr[1] + curr[2];
    }

    // N -> Normal Cases, S -> Special Cases (Around the Cube)
    // Conditions for all 6 cube faces are shown using comments.
    private static boolean moveAroundTheCube(Map<List<Integer>, Character> board, int[] curr) {
        int r = curr[0], c = curr[1], dir = curr[2];

        if (r <= n) {
            // Face 1
            if (dir == 0) {
                if (c % n == 0) {
                    // S
                    return move(board, curr, (n * 3) - (r - 1), n * 4, 2);
                } else {
                    // N
                    return move(board, curr, r, c + 1, dir);
                }
            } else if (dir == 1) {
                // N
                return move(board, curr, r + 1, c, dir);
            } else if (dir == 2) {
                if (c % n == 1) {
                    // S
                    return move(board, curr, n + 1, n + r, 1);
                } else {
                    // N
                    return move(board, curr, r, c - 1, dir);
                }
            } else {
                if (r % n == 1) {
                    // S
                    return move(board, curr, n + 1, (c % n == 0) ? 1 : n - ((c % n) - 1), 1);
                } else {
                    // N
                    return move(board, curr, r - 1, c, dir);
                }
            }
        } else if (r <= n * 2) {
            if (c <= n) {
                // Face 2
                if (dir == 0) {
                    // N
                    return move(board, curr, r, c + 1, dir);
                } else if (dir == 1) {
                    if (r % n == 0) {
                        // S
                        return move(board, curr, n * 3, (n * 3) - (c - 1), 3);
                    } else {
                        // N
                        return move(board, curr, r + 1, c, dir);
                    }
                } else if (dir == 2) {
                    if (c == 1) {
                        // S
                        return move(board, curr, n * 3, (r % n == 0) ? ((n * 3) + 1) : (n * 4) - (r % n - 1), 3);
                    } else {
                        // N
                        return move(board, curr, r, c - 1, dir);
                    }
                } else {
                    if (r % n == 1) {
                        // S
                        return move(board, curr, 1, (n * 3) - (c - 1), 1);
                    } else {
                        // N
                        return move(board, curr, r - 1, c, dir);
                    }
                }
            } else if (c <= n * 2) {
                // Face 3
                if (dir == 0) {
                    // N
                    return move(board, curr, r, c + 1, dir);
                } else if (dir == 1) {
                    if (r % n == 0) {
                        // S
                        return move(board, curr, (c % n == 0) ? ((n * 2) + 1) : (n * 3) - ((c % n) - 1), (n * 2) + 1, 0);
                    } else {
                        // N
                        return move(board, curr, r + 1, c, dir);
                    }
                } else if (dir == 2) {
                    // N
                    return move(board, curr, r, c - 1, dir);
                } else {
                    if (r % n == 1) {
                        // S
                        return move(board, curr, (c % n == 0) ? n : (c % n), (n * 2) + 1, 0);
                    } else {
                        // N
                        return move(board, curr, r - 1, c, dir);
                    }
                }
            } else {
                // Face 4
                if (dir == 0) {
                    if (c % n == 0) {
                        // S
                        return move(board, curr, (n * 2) + 1, (r % n == 0) ? ((n * 3) + 1) : (n * 4) - ((r % n) - 1), 1);
                    } else {
                        // N
                        return move(board, curr, r, c + 1, dir);
                    }
                } else if (dir == 1) {
                    // N
                    return move(board, curr, r + 1, c, dir);
                } else if (dir == 2) {
                    // N
                    return move(board, curr, r, c - 1, dir);
                } else {
                    // N
                    return move(board, curr, r - 1, c, dir);
                }
            }
        } else {
            if (c <= n * 3) {
                // Face 5
                if (dir == 0) {
                    // N
                    return move(board, curr, r, c + 1, dir);
                } else if (dir == 1) {
                    if (r % n == 0) {
                        // S
                        return move(board, curr, n * 2, (c % n == 0) ? 1 : n - (c % n) + 1, 3);
                    } else {
                        // N
                        return move(board, curr, r + 1, c, dir);
                    }
                } else if (dir == 2) {
                    if (c % n == 1) {
                        // S
                        return move(board, curr, n * 2, (r % n == 0) ? (n + 1) : (n * 2) - ((r % n) - 1), 3);
                    } else {
                        // N
                        return move(board, curr, r, c - 1, dir);
                    }
                } else {
                    // N
                    return move(board, curr, r - 1, c, dir);
                }
            } else {
                // Face 6
                if (dir == 0) {
                    if (c % n == 0) {
                        // S
                        return move(board, curr, (r % n == 0) ? 1 : n - ((r % n) - 1), n * 3, 2);
                    } else {
                        // N
                        return move(board, curr, r, c + 1, dir);
                    }
                } else if (dir == 1) {
                    if (r % n == 0) {
                        // S
                        return move(board, curr, (c % n == 0) ? (n + 1) : (n * 2) - ((c % n) - 1), 1, 0);
                    } else {
                        // N
                        return move(board, curr, r + 1, c, dir);
                    }
                } else if (dir == 2) {
                    // N
                    return move(board, curr, r, c - 1, dir);
                } else {
                    if (r % n == 1) {
                        // S
                        return move(board, curr, (c % n == 0) ? (n + 1) : (n * 2) - ((c % n) - 1), (n * 3), 2);
                    } else {
                        // N
                        return move(board, curr, r - 1, c, dir);
                    }
                }
            }
        }
    }

    private static boolean move(Map<List<Integer>, Character> board, int[] curr, int r, int c, int dir) {
        if (board.get(Arrays.asList(r, c)) == '#') {
            return false;
        }
        curr[0] = r;
        curr[1] = c;
        curr[2] = dir;
        return true;
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

        n = inputBoard.size() / 3;
        inputCommandString = sc.nextLine();
        sc.close();
    }
}
