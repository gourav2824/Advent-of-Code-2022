package gourav.adventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static gourav.adventOfCode.utils.AOCUtils.getListOfStringsFromInput;

public class Day5 {
    public static void main(String[] args) {
        final List<String> steps = getListOfStringsFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(getStacksFromInput(), steps));
        System.out.println("Puzzle 2 Answer = " + puzzle2(getStacksFromInput(), steps));
    }

    private static String puzzle1(List<Stack<Character>> stacks, List<String> steps) {
        for (String step : steps) {
            int quantity = Integer.parseInt(step.split(" ")[1]);
            int src = Integer.parseInt(step.split(" ")[3]) - 1;
            int dest = Integer.parseInt(step.split(" ")[5]) - 1;

            for (int i = 0; i < quantity; i++) {
                stacks.get(dest).push(stacks.get(src).pop());
            }
        }

        StringBuilder ans = new StringBuilder();
        for (Stack<Character> stack : stacks) {
            ans.append(stack.peek());
        }

        return ans.toString();
    }

    private static String puzzle2(List<Stack<Character>> stacks, List<String> steps) {
        for (String step : steps) {
            int quantity = Integer.parseInt(step.split(" ")[1]);
            int src = Integer.parseInt(step.split(" ")[3]) - 1;
            int dest = Integer.parseInt(step.split(" ")[5]) - 1;

            final Stack<Character> crates = new Stack<>();
            for (int i = 0; i < quantity; i++) {
                crates.push(stacks.get(src).pop());
            }

            while (crates.size() > 0) {
                stacks.get(dest).push(crates.pop());
            }
        }

        StringBuilder ans = new StringBuilder();
        for (Stack<Character> stack : stacks) {
            ans.append(stack.peek());
        }

        return ans.toString();
    }

    private static List<Stack<Character>> getStacksFromInput() {
        final char[][] inputStacks = {
                {'T', 'R', 'D', 'H', 'Q', 'N', 'P', 'B'},
                {'V', 'T', 'J', 'B', 'G', 'W'},
                {'Q', 'M', 'V', 'S', 'D', 'H', 'R', 'N'},
                {'C', 'M', 'N', 'Z', 'P'},
                {'B', 'Z', 'D'},
                {'Z', 'W', 'C', 'V'},
                {'S', 'L', 'Q', 'V', 'C', 'N', 'Z', 'G'},
                {'V', 'N', 'D', 'M', 'J', 'G', 'L'},
                {'G', 'C', 'Z', 'F', 'M', 'P', 'T'}
        };

        List<Stack<Character>> stacks = new ArrayList<>();

        for (char[] inputStack : inputStacks) {
            final Stack<Character> stack = new Stack<>();
            for (int i = inputStack.length - 1; i >= 0; i--) {
                stack.push(inputStack[i]);
            }
            stacks.add(stack);
        }

        return stacks;
    }
}
