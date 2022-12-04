package gourav.adventOfCode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static gourav.adventOfCode.utils.AOCUtils.getListOfStringsFromInput;

public class Day3 {
    public static void main(String[] args) {
        final List<String> rucksacks = getListOfStringsFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(rucksacks));
        System.out.println("Puzzle 2 Answer = " + puzzle2(rucksacks));
    }

    private static int puzzle1(List<String> rucksacks) {
        int sum = 0;

        for (String rucksack : rucksacks) {
            Set<Character> set = new HashSet<>();
            final int n = rucksack.length();
            for (int i = 0; i < n / 2; i++) {
                set.add(rucksack.charAt(i));
            }

            for (int i = n / 2; i < n; i++) {
                final char ch = rucksack.charAt(i);
                if (set.contains(ch)) {
                    sum += getPriorityValue(ch);
                    break;
                }
            }
        }

        return sum;
    }

    private static int puzzle2(List<String> rucksacks) {
        int sum = 0;

        for (int i = 0; i < rucksacks.size(); i++) {
            Set<Character> set1 = new HashSet<>();
            for (char ch : rucksacks.get(i++).toCharArray()) {
                set1.add(ch);
            }

            Set<Character> set2 = new HashSet<>();
            for (char ch : rucksacks.get(i++).toCharArray()) {
                if (set1.contains(ch)) {
                    set2.add(ch);
                }
            }

            for (char ch : rucksacks.get(i).toCharArray()) {
                if (set2.contains(ch)) {
                    sum += getPriorityValue(ch);
                    break;
                }
            }
        }

        return sum;
    }

    private static int getPriorityValue(char ch) {
        if (ch - 'A' < 26) return ch - 'A' + 27;
        else return ch - 'a' + 1;
    }
}
