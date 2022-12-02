package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day2 {
    private static final int LOSS = 0;
    private static final int DRAW = 3;
    private static final int WIN = 6;

    public static void main(String[] args) {
        final List<char[]> strategy = getStrategyGuideFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(strategy));
        System.out.println("Puzzle 2 Answer = " + puzzle2(strategy));
    }

    private static int puzzle1(List<char[]> strategy) {
        int sum = 0;

        for (char[] round : strategy) {
            int opponent = round[0] - 'A';
            int response = round[1] - 'X';
            sum += getGameResultBasedOnResponse(opponent, response);
        }

        return sum;
    }

    private static int puzzle2(List<char[]> strategy) {
        int sum = 0;

        for (char[] round : strategy) {
            int opponent = round[0] - 'A';
            int outcome = round[1] - 'X';
            sum += getGameResultBasedOnOutcome(opponent, outcome);
        }

        return sum;
    }

    private static int getGameResultBasedOnResponse(int opponent, int response) {
        if (opponent == response) {
            return DRAW + response + 1;
        } else if ((opponent + 1) % 3 == response) {
            return WIN + response + 1;
        } else {
            return LOSS + response + 1;
        }
    }

    private static int getGameResultBasedOnOutcome(int opponent, int outcome) {
        if (outcome == 0) {
            int response = ((opponent - 1) + 3) % 3;
            return LOSS + response + 1;
        } else if (outcome == 1) {
            return DRAW + opponent + 1;
        } else {
            int response = (opponent + 1) % 3;
            return WIN + response + 1;
        }
    }

    private static List<char[]> getStrategyGuideFromInput() {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Please provide your input:");
        String input = sc.nextLine();

        List<char[]> strategy = new ArrayList<>();

        while (input.length() > 0) {
            char opponent = input.split(" ")[0].charAt(0);
            char response = input.split(" ")[1].charAt(0);
            strategy.add(new char[]{opponent, response});
            input = sc.nextLine();
        }

        sc.close();
        return strategy;
    }
}
