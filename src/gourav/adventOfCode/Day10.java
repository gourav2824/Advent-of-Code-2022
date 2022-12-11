package gourav.adventOfCode;

import java.util.Arrays;
import java.util.List;

import static gourav.adventOfCode.utils.AOCUtils.getListOfStringsFromInput;

public class Day10 {
    public static void main(String[] args) {
        final List<String> commands = getListOfStringsFromInput();
        System.out.println("Puzzle 1 Answer = " + puzzle1(commands));
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
}
