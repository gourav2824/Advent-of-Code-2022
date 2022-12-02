package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day1 {
    public static void main(String[] args) {
        final List<Integer> calories = getCaloriesFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(calories));
        System.out.println("Puzzle 2 Answer = " + puzzle2(calories));
    }

    private static int puzzle1(List<Integer> calories) {
        return getMax(calories);
    }

    private static int puzzle2(List<Integer> calories) {
        calories.sort(Collections.reverseOrder());
        return calories.get(0) + calories.get(1) + calories.get(2);
    }

    private static int getMax(List<Integer> list) {
        int max = 0;
        for (int val : list) {
            max = Math.max(max, val);
        }
        return max;
    }

    private static List<Integer> getCaloriesFromInput() {
        final Scanner sc = new Scanner(System.in);
        List<Integer> calories = new ArrayList<>();
        String input = sc.nextLine();
        int sum = 0;

        while (input.length() > 0) {
            sum += Integer.parseInt(input);
            input = sc.nextLine();

            if (input.length() == 0) {
                calories.add(sum);
                sum = 0;
                input = sc.nextLine();
            }
        }

        sc.close();
        return calories;
    }
}
