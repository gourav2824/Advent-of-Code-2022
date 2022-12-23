package gourav.adventOfCode;

import java.util.HashSet;
import java.util.Set;

import static gourav.adventOfCode.utils.AOCUtils.getStringFromInput;

public class Day06 {
    public static void main(String[] args) {
        final String input = getStringFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(input));
        System.out.println("Puzzle 2 Answer = " + puzzle2(input));
    }

    private static int puzzle1(String input) {
        for (int i = 3; i < input.length(); i++) {
            int a = input.charAt(i - 3), b = input.charAt(i - 2), c = input.charAt(i - 1), d = input.charAt(i);
            if (a == b || a == c || a == d || b == c || b == d || c == d) continue;
            return i + 1;
        }
        return -1;
    }

    private static int puzzle2(String input) {
        final Set<Character> set = new HashSet<>();
        int i = 0, j = 0;

        while (j < input.length()) {
            char ch = input.charAt(j);
            while (set.contains(ch)) {
                set.remove(input.charAt(i++));
            }
            set.add(ch);
            if (j - i + 1 == 14) return j + 1;
            j++;
        }

        return -1;
    }
}
